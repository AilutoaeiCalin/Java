package catalog.util;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import catalog.model.Catalog;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerReportBuilder {

    private FreemarkerReportBuilder() {
    }

    public static void build(Catalog catalog, String outputFilePath) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_33);
        configuration.setClassLoaderForTemplateLoading(
                Thread.currentThread().getContextClassLoader(),
                "templates"
        );
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);

        Template template = configuration.getTemplate("report.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("catalogName", catalog.getName());
        dataModel.put("resources", catalog.getResources());

        try (Writer writer = new FileWriter(outputFilePath)) {
            template.process(dataModel, writer);
        }
    }
}