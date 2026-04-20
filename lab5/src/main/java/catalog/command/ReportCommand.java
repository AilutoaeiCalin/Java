package catalog.command;



import catalog.exception.ReportGenerationException;
import catalog.repository.CatalogRepository;
import catalog.util.FreemarkerReportBuilder;

import java.awt.Desktop;
import java.io.File;

public class ReportCommand implements Command {
    private final CatalogRepository repository;
    private final String outputFilePath;

    public ReportCommand(CatalogRepository repository, String outputFilePath) {
        this.repository = repository;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void execute() throws Exception {
        try {
            FreemarkerReportBuilder.build(repository.getCatalog(), outputFilePath);

            File reportFile = new File(outputFilePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(reportFile.toURI());
            }

            System.out.println("HTML report generated successfully: " + reportFile.getAbsolutePath());
        } catch (Exception e) {
            throw new ReportGenerationException("Could not generate HTML report using FreeMarker.", e);
        }
    }
}
