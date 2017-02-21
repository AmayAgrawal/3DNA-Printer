/* Author: Amay Agrawal, Birva Patel
 * Project: 3DNA Printer
 * Mentor: Prof. Manish K Gupta
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URISyntaxException;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


@SuppressWarnings("serial")
public class MainFrame extends JFrame{
    public static boolean[][][]StoreCoordinates=new boolean[20][20][20];
    public static int height=0;
    public static int width=0;
    public static int depth=0;
    public static JButton estimateButton;
    public static JButton exportButton;
    public static JButton pdfButton;
    public static JButton csvButton;
   /* public static JButton zoomInButton;
    public static JButton zoomOutButton;
    public static JButton upButton;
    public static JButton downButton;
    public static JButton rightButton;
    public static JButton leftButton;
    public static JButton undoButton;*/
   // public static JButton visualizeButton;
    public static JButton resetButton;
	public static JButton latexButton;
    public static JButton nextButton;
    public static JButton nextButton1;
    public static JButton nextButton2;
    public static JButton fullButton;
//    public static JButton graphButton;

    public static boolean licenseAccepted = false;
    public static boolean isBoundaryCalled=false;
    public static boolean isPDFBoundarySaved=false;
    public static boolean isCSVBoundarySaved=false;
    public static boolean isPDFSaved=false;
    public static boolean isCSVSaved=false;


    public static int GClowerLimit=6;
    public static int GCupperLimit=10;


    public MainFrame(){

        super("3DNA Printer");
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adding JPanels
        JPanel basePanel=new JPanel();
        basePanel.setLayout(new BorderLayout());
        getContentPane().add(basePanel);
        addPanels();

        //configure Panels and Split Panes
        hPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT );
        basePanel.add(hPanel,BorderLayout.CENTER );
        vPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT );
        hPanel.setLeftComponent(leftJPanel );
        hPanel.setRightComponent(vPanel);
        vPanel.setTopComponent(topRightJPanel);
        vPanel.setBottomComponent(bottomRightJPanel );
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);

        //add JMenuBar and JToolBar
        createAndShowGUI();
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
    }
    public void addPanels(){

   /*     //Button Panel
        leftJPanel = new JPanel();
        leftJPanel.setLayout( new BorderLayout() );
        leftJPanel.setBackground(Color.DARK_GRAY);
        // Add leftJPanel Images and Buttons
        ImageIcon zoomin = new ImageIcon("icons/zoomin.png");
        ImageIcon zoomout = new ImageIcon("icons/zoomout.png");
        ImageIcon upArrow=new ImageIcon("icons/arrow-up.png");
        ImageIcon downArrow=new ImageIcon("icons/arrow-down.png");
        ImageIcon leftArrow=new ImageIcon("icons/arrow-left.png");
        ImageIcon rightArrow=new ImageIcon("icons/arrow-right.png");
        ImageIcon reset=new ImageIcon("icons/reset.png");
        ImageIcon undo =new ImageIcon("icons/undo.png");

        leftJPanel.add(mainPanel,BorderLayout.PAGE_START);*/

        //Main Display Panel
        topRightJPanel = new JPanel();
        topRightJPanel.setLayout( new BorderLayout() );
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body background=\"file:icons/platonic.jpg\" style=\"font-family:Century Gothic\"><img src='file:icons/3dnaprinterlogo.png' width='200' height='200'>" +
                "<h1>&nbsp;3DNA Printer: A tool for Automated DNA Origami</h1>" +
                "<h3>&nbsp;3DNA Printer can be used to just upload any complex 3D shape(.obj) file and provide stable,<br>" +
                "&nbsp;error-free sequences to envisage the structure drawn at nano-scale.<br> " +
                "&nbsp;The software is based on the research led by " +
                "<a href=\"https://www.linkedin.com/in/r%C3%A9mi-veneziano-32717783\">Rémi Veneziano</a>, " +
                "<a href=\"http://yin.hms.harvard.edu/people/ong.luvena/index.html\"> Sakul Ratanalert</a>," +
                "<a href=\"https://www.bcm.edu/people/view/kaiming-zhang/5de784bc-ecf2-11e3-a42d-005056b104be\"> Kaiming Zhang</a><br>&nbsp  " +
                "<a href=\"https://www.linkedin.com/in/fei-zhang-a1974450\"> Fei Zhang</a>, "+
                "<a href=\"https://www.linkedin.com/in/hao-yan-9799507\">  Hao Yan</a>,"+
                "<a href=\"https://www.bcm.edu/people/view/wah-chiu-ph-d/b279d3f6-ffed-11e2-be68-080027880ca6\">  Wah Chiu</a> and "+
                "<a href=\"https://www.linkedin.com/in/markbathe\">  Mark Bathe</a> on <a href=\"http://science.sciencemag.org/content/early/2016/05/25/science.aaf4388\">" +
                "Designer nanoscale DNA assemblies programmed from the top down.</a></h3>" +
                "<h3>&nbsp;3DNA Printer has been developed for the sole-purpose of generating a user-friendly, " +
                "interactive<br> &nbsp;environment for users to envisage DNA structures, and get the " +
                "actual DNA sequences required<br> &nbsp;to make the structure physically.</h3>" +
                "</body></html>");
        editorPane.addHyperlinkListener(
                new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hyperLinkEvent) {
                // TODO Auto-generated method stub
                if(hyperLinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        Desktop.getDesktop().browse(hyperLinkEvent.getURL().toURI());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        topRightJPanel.add(editorScrollPane);
    }
    public void createAndShowGUI(){

        //add ToolBar
        JToolBar toolBar=new JToolBar("Quick Links");
        toolBar.setBackground(Color.DARK_GRAY);
        this.add(toolBar,BorderLayout.NORTH);

        ImageIcon newi = new ImageIcon("icons/new-canvas.png");
      //  ImageIcon editi = new ImageIcon("images/edit.png");
        ImageIcon estimate = new ImageIcon("icons/estimator.png");
        ImageIcon importData = new ImageIcon("icons/import.png");
        ImageIcon exportData = new ImageIcon("icons/export.png");
        ImageIcon SaveAsPDF = new ImageIcon("icons/pdf.png");
        ImageIcon SaveAsLATEX = new ImageIcon("icons/latex.png");
        ImageIcon reset=new ImageIcon("icons/reset.png");
     //   ImageIcon viewGraph = new ImageIcon("icons/analysis.png");
        ImageIcon SaveAsCSV = new ImageIcon("icons/csv.png");
       // ImageIcon visualize = new ImageIcon("icons/visualize.png");
        ImageIcon ytube = new ImageIcon("icons/youtube.png");
     //   ImageIcon twit = new ImageIcon("icons/twitter.png");
        ImageIcon fb = new ImageIcon("icons/facebook.png");
    //    ImageIcon quora = new ImageIcon("icons/quora.png");
        ImageIcon about = new ImageIcon("icons/about.png");
        ImageIcon usermanual = new ImageIcon("icons/usermanual.png");
        ImageIcon feedback = new ImageIcon("icons/feedback.png");
        ImageIcon demo = new ImageIcon("icons/demo.png");
        ImageIcon STEP1 = new ImageIcon("icons/STEP1.png");
        ImageIcon STEP2 = new ImageIcon("icons/STEP2.png");
        ImageIcon STEP3 = new ImageIcon("icons/STEP3.png");
        ImageIcon full = new ImageIcon("icons/visualize.png");
        
        /*JButton newButton = new JButton(newi);
        newButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        newButton.setHorizontalTextPosition(AbstractButton.CENTER);
        newButton.setBackground(Color.DARK_GRAY);*/
/*
        JButton editButton = new JButton("Edit Dimensions",editi);
        editButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        editButton.setHorizontalTextPosition(AbstractButton.CENTER);
        editButton.setBackground(Color.DARK_GRAY);
*/
        estimateButton = new JButton(estimate);
        estimateButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        estimateButton.setHorizontalTextPosition(AbstractButton.CENTER);
        estimateButton.setBackground(Color.DARK_GRAY);

        JButton importButton = new JButton(importData);
        importButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        importButton.setHorizontalTextPosition(AbstractButton.CENTER);
        importButton.setBackground(Color.DARK_GRAY);

        /*exportButton = new JButton(exportData);
        exportButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        exportButton.setHorizontalTextPosition(AbstractButton.CENTER);
        exportButton.setBackground(Color.DARK_GRAY);*/
		
		/*latexButton = new JButton(SaveAsLATEX);
        latexButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        latexButton.setHorizontalTextPosition(AbstractButton.CENTER);
        latexButton.setBackground(Color.DARK_GRAY);*/

        /*pdfButton = new JButton(SaveAsPDF);
        pdfButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        pdfButton.setHorizontalTextPosition(AbstractButton.CENTER);
        pdfButton.setBackground(Color.DARK_GRAY);*/

        /*csvButton = new JButton(SaveAsCSV);
        csvButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        csvButton.setHorizontalTextPosition(AbstractButton.CENTER);
        csvButton.setBackground(Color.DARK_GRAY);*/

        resetButton = new JButton(reset);
        resetButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        resetButton.setHorizontalTextPosition(AbstractButton.CENTER);
        resetButton.setBackground(Color.DARK_GRAY);

        JButton youtubeButton = new JButton(ytube);
        youtubeButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        youtubeButton.setHorizontalTextPosition(AbstractButton.CENTER);
        youtubeButton.setBackground(Color.DARK_GRAY);

        JButton fbButton = new JButton(fb);
        fbButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        fbButton.setHorizontalTextPosition(AbstractButton.CENTER);
        fbButton.setBackground(Color.DARK_GRAY);

     /*   JButton twitterButton = new JButton(twit);
        twitterButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        twitterButton.setHorizontalTextPosition(AbstractButton.CENTER);
        twitterButton.setBackground(Color.DARK_GRAY);

        JButton quoraButton = new JButton(quora);
        quoraButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        quoraButton.setHorizontalTextPosition(AbstractButton.CENTER);
        quoraButton.setBackground(Color.DARK_GRAY);*/

        JButton aboutButton = new JButton(about);
        aboutButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        aboutButton.setHorizontalTextPosition(AbstractButton.CENTER);
        aboutButton.setBackground(Color.DARK_GRAY);

        JButton feedbackButton = new JButton(feedback);
        feedbackButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        feedbackButton.setHorizontalTextPosition(AbstractButton.CENTER);
        feedbackButton.setBackground(Color.DARK_GRAY);

        JButton userManualButton = new JButton(usermanual);
        userManualButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        userManualButton.setHorizontalTextPosition(AbstractButton.CENTER);
        userManualButton.setBackground(Color.DARK_GRAY);

        JButton demoButton = new JButton(demo);
        demoButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        demoButton.setHorizontalTextPosition(AbstractButton.CENTER);
        demoButton.setBackground(Color.DARK_GRAY);

        nextButton = new JButton(STEP1);
        demoButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        demoButton.setHorizontalTextPosition(AbstractButton.CENTER);
        demoButton.setBackground(Color.DARK_GRAY);
        
        nextButton1 = new JButton(STEP2);
        demoButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        demoButton.setHorizontalTextPosition(AbstractButton.CENTER);
        demoButton.setBackground(Color.DARK_GRAY);
        
        nextButton2 = new JButton(STEP3);
        demoButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        demoButton.setHorizontalTextPosition(AbstractButton.CENTER);
        demoButton.setBackground(Color.DARK_GRAY);
        
        fullButton = new JButton(full);
        demoButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        demoButton.setHorizontalTextPosition(AbstractButton.CENTER);
        demoButton.setBackground(Color.DARK_GRAY);
        
        // nextButton.setEnabled(false);
      //  next1Button.setEnabled(false);


        //newButton.setToolTipText("Open New Canvas");
//        editButton.setToolTipText("Edit Dimensions");
        estimateButton.setToolTipText("Estimator");
        importButton.setToolTipText("Import File");
       // exportButton.setToolTipText("Export File");
		//latexButton.setToolTipText("Save as LateX source file");
        //pdfButton.setToolTipText("Save as PDF");
//        graphButton.setToolTipText("3DNA domain analysis");
        //csvButton.setToolTipText("Save as .csv");
       // visualizeButton.setToolTipText("visualize");
        demoButton.setToolTipText("Software Demo");
        aboutButton.setToolTipText("About 3DNA Printer");
        feedbackButton.setToolTipText("3DNA Printer Feedback Page");
        userManualButton.setToolTipText("3DNA Printer User Manual");
        nextButton.setToolTipText("Spanning Tree");
        nextButton1.setToolTipText("Eulerian Circuit");
        nextButton2.setToolTipText("Generate DNA sequences");
        fullButton.setToolTipText("Generate Sequences directly");
        //adding different buttons to the toolbar
       // toolBar.add(newButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(importButton);
        //toolBar.add(Box.createHorizontalStrut(15));
       // toolBar.add(exportButton);
        //toolBar.add(Box.createHorizontalStrut(15));
        //toolBar.add(latexButton);
        //toolBar.add(Box.createHorizontalStrut(15));
        //toolBar.add(pdfButton);
        //toolBar.add(Box.createHorizontalStrut(15));
        //toolBar.add(csvButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(estimateButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(resetButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(aboutButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(userManualButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(feedbackButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(demoButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(fbButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(youtubeButton);
        toolBar.add(Box.createHorizontalStrut(15));
     /*   toolBar.add(quoraButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(twitterButton);
        toolBar.add(Box.createHorizontalStrut(15));*/
        toolBar.add(nextButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(nextButton1);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(nextButton2);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(fullButton);
        toolBar.add(Box.createHorizontalStrut(15));

        FalseEnableContent();

        estimateButton.addActionListener(new EstimatorActionListener());
        //newButton.addActionListener(new CanvasActionListener());
//      editButton.addActionListener(new EditDimensionsActionListener());
		//latexButton.addActionListener(new SaveLatexActionListener());
        //pdfButton.addActionListener(new SavePDFListener());
        //csvButton.addActionListener(new SaveDetailedDataListener());
        importButton.addActionListener(new ImportActionListener());
        //exportButton.addActionListener(new ExportActionListener());
        youtubeButton.addActionListener(new YoutubeActionListener());
        fbButton.addActionListener(new FacebookActionListener());
     //   twitterButton.addActionListener(new TwitterActionListener());
      //  quoraButton.addActionListener(new QuoraActionListener());
        //visualizeButton.addActionListener(new VisualizeActionListener());
        userManualButton.addActionListener(new UserManualActionListener());
        feedbackButton.addActionListener(new ProductFeedbackActionListener());
        aboutButton.addActionListener(new AboutActionListener());
        demoButton.addActionListener(new ProductDemoActionListener());
        resetButton.addActionListener(new ResetButtonActionListener());
        nextButton.addActionListener(new nextActionListener());
        nextButton1.addActionListener(new next1ActionListener());
        nextButton2.addActionListener(new next2ActionListener());
        fullButton.addActionListener(new fullActionListener());
    }

    public static void FalseEnableContent(){
       // exportButton.setEnabled(false);
        //latexButton.setEnabled(false);
		//csvButton.setEnabled(false);
        //pdfButton.setEnabled(false);
        estimateButton.setEnabled(false);
    /*    zoomInButton.setEnabled(false);
        zoomOutButton.setEnabled(false);
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);*/
       // visualizeButton.setEnabled(false);
        resetButton.setEnabled(false);
//        graphButton.setEnabled(false);
 //       undoButton.setEnabled(false);
        nextButton.setEnabled(false);
        nextButton1.setEnabled(false);
        nextButton2.setEnabled(false);
        fullButton.setEnabled(false);

    }

    public static void TrueEnableNext()
    {
        nextButton.setEnabled(true);
        nextButton1.setEnabled(true);
        nextButton2.setEnabled(true);
        fullButton.setEnabled(true);
    }
    public static void TrueEnableContent(){
       // exportButton.setEnabled(true);
		//latexButton.setEnabled(true);
        //csvButton.setEnabled(true);
        //pdfButton.setEnabled(true);
        estimateButton.setEnabled(true);
  /*      zoomInButton.setEnabled(true);
        zoomOutButton.setEnabled(true);
        upButton.setEnabled(true);
        downButton.setEnabled(true);
        leftButton.setEnabled(true);
        rightButton.setEnabled(true);*/
       // visualizeButton.setEnabled(true);
        resetButton.setEnabled(true);
  //      undoButton.setEnabled(true);
    }

 /*   public static void enableGraph(){
        graphButton.setEnabled(true);
    }*/

    //License Accept Code
    public void acceptRejectLicense() {
        try {
            File tempFile = new File("TempFile");
            FileWriter fileWriter = new FileWriter(tempFile);
            fileWriter.write("TempFile");
            fileWriter.close();

            String filePath = tempFile.getAbsolutePath();
            System.out.println(filePath);
            filePath = filePath.substring(0, filePath.indexOf("TempFile"));

            if (osName.toLowerCase().contains("mac")) {
                filePath = filePath.concat("configFile");
                configFile = new File(filePath);
            } else {
                filePath = filePath.concat("configFile");
                configFile = new File(filePath);
            }

            FileReader frConfig = new FileReader(configFile);
            BufferedReader bufferedReader = new BufferedReader(frConfig);

            String configFileData = "";

            while ((configFileData = bufferedReader.readLine()) != null) {
                System.out.println(configFileData);
                if (configFileData.contains("License:1")) {
                    licenseAccepted = true;
                }
            }
            bufferedReader.close();
            tempFile.delete();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Exception occurred." + e1,
                    "Error!", JOptionPane.INFORMATION_MESSAGE);
        }

        if (!licenseAccepted) {
            JFrame licenseFrame = new JFrame("License");
            JLabel licenseText = new JLabel("Sample License");
            JScrollPane jScrollPane = new JScrollPane(licenseText);
            JPanel jPanel = new JPanel();

            final JButton acceptButton = new JButton("Accept");
            final JButton rejectButton = new JButton("Reject");

            WindowListener exitListener = new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    int userChoice = JOptionPane.showConfirmDialog(null, "Are you sure ?",
                            "License Acceptance", JOptionPane.YES_NO_OPTION);
                    if (userChoice == 0)
                    {
                        System.exit(0);
                    }
                }
            };

            licenseFrame.addWindowListener(exitListener);

            try {
                File tempFile = new File("TempFile");
                FileWriter fileWriter = new FileWriter(tempFile);
                fileWriter.write("TempFile");
                fileWriter.close();

                String filePath = tempFile.getAbsolutePath();
                System.out.println(filePath);
                filePath = filePath.substring(0, filePath.indexOf("TempFile"));

                if (osName.toLowerCase().contains("mac")) {
                    filePath = filePath.concat("docs/License.txt");
                } else {
                    filePath = filePath.concat("/docs/License.txt");
                }

                FileReader fileReader = new FileReader(new File(filePath));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String tempString, licenseTextString = "<html><br/>";

                // Write File content in JLabel
                while ((tempString = bufferedReader.readLine()) != null) {
                    licenseTextString = licenseTextString.concat(tempString + "<br/>");
                }
                licenseTextString = licenseTextString.concat("<br/><br/></html>");
                licenseText.setText(licenseTextString);
                fileReader.close();
                tempFile.delete();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Exception occurred.",
                        "Error!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e2) {
                e2.printStackTrace(System.out);
                JOptionPane.showMessageDialog(null, "Exception occurred.",
                        "Error!", JOptionPane.INFORMATION_MESSAGE);
            }

            jPanel.setLayout(new GridLayout(1, 2));
            jPanel.add(acceptButton);
            jPanel.add(rejectButton);

            jScrollPane.getViewport().setBackground(Color.white);

            licenseFrame.getContentPane().add(jScrollPane,BorderLayout.CENTER);
            licenseFrame.getContentPane().add(jPanel,BorderLayout.PAGE_END);

            Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

            licenseFrame.setBounds(screenDimensions.width / 4, screenDimensions.height/ 4,
                    screenDimensions.width / 2, screenDimensions.height / 2);
            licenseFrame.setResizable(false);
            licenseFrame.setVisible(true);

            acceptButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    try {
                        FileWriter fileWriter = new FileWriter(configFile);
                        fileWriter.write("License:1");
                        fileWriter.close();

                        JOptionPane.showMessageDialog(null, "Thank you for accepting the license.",
                                "Success!", JOptionPane.INFORMATION_MESSAGE);
                        acceptButton.setEnabled(false);
                        licenseAccepted = true;
                        File projectDirectory=new File(System.getProperty("user.home")+"/Desktop/3DNA_Workspace");
                        projectDirectory.mkdirs();
                        JOptionPane.showMessageDialog(null,"A new workspace has been created at: "+System.getProperty("user.home")
                        +"Desktop/3DNA_Workspace");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "Exception occurred.",
                                "Error!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            rejectButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    int userChoice = JOptionPane.showConfirmDialog(null, "Are you sure ?",
                            "License Acceptance", JOptionPane.YES_NO_OPTION);
                    if (userChoice == 0)
                    {
                        System.exit(0);
                    }
                }
            });

            while (acceptButton.isEnabled()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                    JOptionPane.showMessageDialog(null, "Exception occurred.",
                            "Error!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            licenseFrame.dispose();
        }
    }
    @SuppressWarnings("unused")
    public static void main(String []args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception es){
            JOptionPane.showMessageDialog(null, "Exception Occurred with the GUI Toolkit","Exception",JOptionPane.ERROR_MESSAGE);
        }
        mainFrame=new MainFrame();
        ImageIcon img = new ImageIcon("icons/logo_half.png");
        mainFrame.setIconImage(img.getImage());
        //mainFrame.acceptRejectLicense();
        
    }

    public static JSplitPane vPanel;
    public static JSplitPane hPanel;
    public static JPanel basePanel;
    public static JPanel leftJPanel;
    public static JPanel topRightJPanel;
    public static JPanel bottomRightJPanel;
    public static String osName = System.getProperty("os.name");
    public static File configFile;
    public static MainFrame mainFrame;
    public static String projectName;
    public static String projectPath;
    public static boolean isProjectCreated;
    public static boolean isImported=false;
}
