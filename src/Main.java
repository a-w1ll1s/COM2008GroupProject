import views.MainFrame;
import views.Manager.ManagerView;
import views.Staff.PendingOrderQueueView;
import models.database.*;
import models.business.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame("Application"));
    }
}
