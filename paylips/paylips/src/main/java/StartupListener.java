import services.xls.XlsImportServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        XlsImportServiceImpl xlsImportService = new XlsImportServiceImpl();
        xlsImportService.importXls("paylips/paylips/src/teste.xls");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


}
