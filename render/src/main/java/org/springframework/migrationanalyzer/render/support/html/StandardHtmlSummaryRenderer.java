/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.migrationanalyzer.render.support.html;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.migrationanalyzer.analyze.AnalysisResult;
import org.springframework.migrationanalyzer.analyze.AnalysisResultEntry;
import org.springframework.migrationanalyzer.render.MigrationCost;
import org.springframework.migrationanalyzer.render.ModelAndView;
import org.springframework.migrationanalyzer.render.SummaryController;
import org.springframework.migrationanalyzer.util.IoUtils;

@SuppressWarnings("rawtypes")
final class StandardHtmlSummaryRenderer implements HtmlSummaryRenderer {

    private static final String VIEW_NAME_SUMMARY_HEADER = "summary-header";

    private static final String VIEW_NAME_SUMMARY_FOOTER = "summary-footer";

    private static final String VIEW_NAME_GUIDANCE_HEADER = "guidance-header";

    private static final String VIEW_NAME_GUIDANCE_FOOTER = "guidance-footer";

    private static final String VIEW_NAME_GUIDANCE_CATEGORY_HEADER = "guidance-category-header";

    private static final String VIEW_NAME_GUIDANCE_CATEGORY_FOOTER = "guidance-category-footer";

    private static final String VIEW_NAME_GUIDANCE_ENTRY_HEADER = "guidance-entry-header";

    private static final String VIEW_NAME_GUIDANCE_ENTRY_FOOTER = "guidance-entry-footer";

    private final RootAwareOutputPathGenerator outputPathGenerator;

    private final WriterFactory writerFactory;

    private final Set<SummaryController> summaryControllers;

    private final ViewRenderer viewRenderer;

    StandardHtmlSummaryRenderer(Set<SummaryController> summaryControllers, ViewRenderer viewRenderer,
        RootAwareOutputPathGenerator outputPathGenerator, WriterFactory writerFactory) {
        this.summaryControllers = summaryControllers;
        this.viewRenderer = viewRenderer;
        this.outputPathGenerator = outputPathGenerator;
        this.writerFactory = writerFactory;
    }

    @Override
    public void renderSummary(AnalysisResult analysisResult) {
        Writer writer = null;
        try {
            String summaryPath = this.outputPathGenerator.generatePathForSummary();
            writer = this.writerFactory.createWriter(summaryPath);

            this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_SUMMARY_HEADER, writer);

            this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_GUIDANCE_HEADER, writer);

            renderMigrationGuidance(analysisResult, writer, MigrationCost.LOW);
            renderMigrationGuidance(analysisResult, writer, MigrationCost.MEDIUM);
            renderMigrationGuidance(analysisResult, writer, MigrationCost.HIGH);

            this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_GUIDANCE_FOOTER, writer);

            for (Class<?> resultType : analysisResult.getResultTypes()) {
                this.viewRenderer.render(resultType, analysisResult.getResultEntries(resultType), this.summaryControllers, writer,
                    new LocationAwareOutputPathGenerator(this.outputPathGenerator, summaryPath));
            }

            this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_SUMMARY_FOOTER, writer);
        } finally {
            IoUtils.closeQuietly(writer);
        }

    }

    private void renderMigrationGuidance(AnalysisResult analysisResult, Writer writer, MigrationCost migrationCost) {
        List<ModelAndView> modelsAndViews = new ArrayList<ModelAndView>();

        for (Class<?> resultType : analysisResult.getResultTypes()) {
            modelsAndViews.addAll(renderMigrationGuidance(resultType, analysisResult.getResultEntries(resultType), migrationCost));
        }

        if (!modelsAndViews.isEmpty()) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("title", migrationCost.toDisplayString());

            this.viewRenderer.renderViewWithModel(VIEW_NAME_GUIDANCE_CATEGORY_HEADER, model, writer);

            for (ModelAndView modelAndView : modelsAndViews) {
                this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_GUIDANCE_ENTRY_HEADER, writer);
                this.viewRenderer.renderViewWithModel(modelAndView.getViewName(), modelAndView.getModel(), writer);
                this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_GUIDANCE_ENTRY_FOOTER, writer);
            }

            this.viewRenderer.renderViewWithEmptyModel(VIEW_NAME_GUIDANCE_CATEGORY_FOOTER, writer);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<ModelAndView> renderMigrationGuidance(Class<?> resultType, Set<AnalysisResultEntry<T>> entries, MigrationCost migrationCost) {
        List<ModelAndView> modelsAndViews = new ArrayList<ModelAndView>();

        for (SummaryController<?> controller : this.summaryControllers) {
            if (controller.canHandle(resultType)) {
                SummaryController<T> specificController = (SummaryController<T>) controller;
                List<ModelAndView> modelAndView = specificController.handle(entries, migrationCost);
                if (modelAndView != null) {
                    modelsAndViews.addAll(modelAndView);
                }
            }
        }

        return modelsAndViews;
    }
}
