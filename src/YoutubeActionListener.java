/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

public class YoutubeActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Desktop desktop = Desktop.getDesktop();
        try {
            URI updateLink = new URI("https://www.youtube.com/channel/UC-VRZQ0VwXALYr1KkbX9lcQ");
            desktop.browse(updateLink);
        } catch (URISyntaxException e1) {
            e1.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Exception occurred.",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e2) {
            e2.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Exception occurred.",
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
        }
	}

}
