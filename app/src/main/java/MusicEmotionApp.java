import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.apache.commons.collections4.BidiMap;

import connection.ConnectionManager;
import utils.StretchIcon;

public class MusicEmotionApp {
    private final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final static ConnectionManager Manager = ConnectionManager.getInstance();
    public static void main(String[] args) throws IOException {
        JTabbedPane menu = new JTabbedPane();
        JPanel preSelectingPanel = new JPanel();
        JPanel firstImagesPanel = new JPanel();
        JPanel comboPanel = new JPanel();
        JPanel postSelectingPanel = new JPanel();
        JPanel secondImagesPanel = new JPanel();
        JPanel songPanel = new JPanel();
        ButtonGroup preImagesButtonGroup = new ButtonGroup();
        ButtonGroup postImagesButtonGroup = new ButtonGroup();

        AtomicReference<String> locationNameString = new AtomicReference<>("");
        AtomicReference<String> locationDescString = new AtomicReference<>("");
        AtomicReference<String> oldLocation = new AtomicReference<>("");
        AtomicReference<String> memoryNameString = new AtomicReference<>("");
        AtomicReference<String> memoryDescString = new AtomicReference<>("");
        AtomicReference<String> toDoNameString = new AtomicReference<>("");
        AtomicReference<String> toDoDescString = new AtomicReference<>("");
        AtomicReference<String> toDoActivityTypeString = new AtomicReference<>("");
        AtomicReference<String> whileDoingNameString = new AtomicReference<>("");
        AtomicReference<String> whileDoingDescString = new AtomicReference<>("");
        AtomicReference<String> whileDoingActivityTypeString = new AtomicReference<>("");

        Random rand = new Random();
        BidiMap<String,String> activityType = Manager.getActivityType();

        TextField nameText = new TextField();
        nameText.setColumns(15);
        TextField surnameText = new TextField();
        surnameText.setColumns(15);
        JButton whileDoing = new JButton("While doing");

        whileDoing.addActionListener( e -> {
            NameDesc tmp = addNewActivityTuple("Insert your activity while listening", activityType);
            whileDoingNameString.set(tmp.name);
            whileDoingDescString.set(tmp.desc);
            whileDoingActivityTypeString.set(tmp.activityType);
        });

        JButton wantToDo = new JButton("Want to do");
        wantToDo.addActionListener( e -> {
            NameDesc tmp = addNewActivityTuple("Insert the activity this listening inspires you to do", activityType);
            toDoNameString.set(tmp.name);
            toDoDescString.set(tmp.desc);
            toDoActivityTypeString.set(tmp.activityType);
        });

        JButton memories = new JButton("Revives memory");
        memories.addActionListener( e -> {
            NameDesc tmp = addNewTuple();
            memoryNameString.set(tmp.name);
            memoryDescString.set(tmp.desc);
        });

        JButton location = new JButton("Location");
        JComboBox<String> locations = new JComboBox<>();
        locations.addItem("");
        Manager.getLocations().forEach((key, value) -> locations.addItem(key + "-" + value));
        location.addActionListener( e -> {
            NameDesc tmp = addNewLocationTuple(locations);
            locationNameString.set(tmp.name);
            locationDescString.set(tmp.desc);
            oldLocation.set(tmp.activityType);
        });

        JComboBox<String> songsCombo = new JComboBox<>();
        BidiMap<String,String> songs =  Manager.getSongs();
        songs.forEach((key,value) -> songsCombo.addItem(value));

        JButton submit = new JButton("Submit");
        submit.addActionListener( event -> {
            String name = nameText.getText();
            String surname = surnameText.getText();
            if(!name.equals("") && !surname.equals("")) {
                System.out.println("Add new query");
                System.out.println(name);
                System.out.println(surname);
                System.out.println(String.valueOf(songs.getKey(songsCombo.getSelectedItem())));
                System.out.println(whileDoingNameString.get());
                System.out.println(whileDoingDescString.get());
                System.out.println(whileDoingActivityTypeString.get());
                System.out.println(toDoNameString.get());
                System.out.println(toDoDescString.get());
                System.out.println(toDoActivityTypeString.get());
                System.out.println(memoryNameString.get());
                System.out.println(memoryDescString.get());
                System.out.println(locationNameString.get());
                System.out.println(locationDescString.get());
                System.out.println(oldLocation.get());
                System.out.println(preImagesButtonGroup.getSelection().getActionCommand());
                System.out.println(postImagesButtonGroup.getSelection().getActionCommand());
                Manager.addQuery(name,surname,String.valueOf(songs.getKey(songsCombo.getSelectedItem())),
                    whileDoingNameString.get(),whileDoingDescString.get(),whileDoingActivityTypeString.get(),
                    toDoNameString.get(), toDoDescString.get(),toDoActivityTypeString.get(),
                    memoryNameString.get(), memoryDescString.get(),
                    locationNameString.get(),locationDescString.get(),oldLocation.get(),
                    preImagesButtonGroup.getSelection().getActionCommand(),
                    postImagesButtonGroup.getSelection().getActionCommand());
            }
        });

        List<String> images = Manager.getImages();
        for(int i=0; i<=2; i++){
            String imgPre = images.get(rand.nextInt(images.size()))+".jpg";
            CustomButton pre = new CustomButton(imgPre, getIcon(imgPre));
            pre.setActionCommand(imgPre.substring(0,imgPre.lastIndexOf(".")));
            pre.setSize(SCREEN_SIZE.width/6,SCREEN_SIZE.height/6);

            String imgPost = images.get(rand.nextInt(images.size()))+".jpg";
            CustomButton post = new CustomButton(imgPost, getIcon(imgPost));
            post.setActionCommand(imgPost.substring(0,imgPost.lastIndexOf(".")));

            preImagesButtonGroup.add(pre);
            postImagesButtonGroup.add(post);
            firstImagesPanel.add(pre);
            secondImagesPanel.add(post);
            preImagesButtonGroup.setSelected(pre.getModel(),true);
            postImagesButtonGroup.setSelected(post.getModel(),true);
        }

        JFrame frame = new JFrame("MusicEmotion");
        JPanel mainPane = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_SIZE.width/2,SCREEN_SIZE.height/2);

        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        preSelectingPanel.setLayout(new FlowLayout());
        firstImagesPanel.setLayout(new FlowLayout());
        comboPanel.setLayout(new FlowLayout());
        postSelectingPanel.setLayout(new FlowLayout());
        songPanel.setLayout(new FlowLayout());
        secondImagesPanel.setLayout(new FlowLayout());
        
        preSelectingPanel.setFont(new Font("Serif", Font.BOLD, 20));
        songPanel.setFont(new Font("Serif", Font.BOLD, 16));
        postSelectingPanel.setFont(new Font("Serif", Font.BOLD, 20));

        preSelectingPanel.add(new Label("Select the image that most represents your mood before listening to such song"));

        songPanel.add(new Label("Name:"));
        songPanel.add(nameText);
        songPanel.add(new Label( "Surname:"));
        songPanel.add(surnameText);
        songPanel.add(songsCombo);

        comboPanel.add(whileDoing);
        comboPanel.add(wantToDo);
        comboPanel.add(location);
        comboPanel.add(memories);

        postSelectingPanel.add(new Label("Select the image that most represents your mood after listening to such song"));

        mainPane.add(preSelectingPanel);
        mainPane.add(firstImagesPanel);
        mainPane.add(songPanel);
        mainPane.add(comboPanel);
        mainPane.add(postSelectingPanel);
        mainPane.add(secondImagesPanel);
        mainPane.add(submit);

        menu.add("Insert your Emotions", mainPane);
        frame.setContentPane(menu);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static StretchIcon getIcon(String name) throws IOException {
        return new StretchIcon(ImageIO.read(Objects.requireNonNull(MusicEmotionApp.class.getClassLoader().getResourceAsStream(name))));
    }

    private static NameDesc addNewTuple(){
        JTextField someName = new JTextField();
        JTextField someDesc = new JTextField();
        JComponent[] inputs = new JComponent[] {
            new JLabel("Name"),
            someName,
            new JLabel("Description"),
            someDesc,
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Insert the memory revived with this listening", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new NameDesc(someName.getText(),someDesc.getText());
        } else {
            return new NameDesc("","");
        }

    }

    private static NameDesc addNewActivityTuple(final String name, final BidiMap<String,String> activityType){
        JTextField someName = new JTextField();
        JTextField someDesc = new JTextField();
        JComboBox<String> activities = new JComboBox<>();
        activityType.values().forEach(activities::addItem);
        JComponent[] inputs = new JComponent[] {
            new JLabel ("Activity Type:"),
            activities,
            new JLabel("Name"),
            someName,
            new JLabel("Description"),
            someDesc,
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, name, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new NameDesc(someName.getText(),someDesc.getText(),activityType.getKey(activities.getSelectedItem()));
        } else {
            return new NameDesc("","");
        }

    }

    private static NameDesc addNewLocationTuple(final JComboBox<String> oldLocations){
        JTextField someName = new JTextField();
        JTextField someDesc = new JTextField();
        JComponent[] inputs = new JComponent[] {
            new JLabel ("Locations:"),
            oldLocations,
            new JLabel("Name"),
            someName,
            new JLabel("Description"),
            someDesc,
        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Choose the location where this listening took place", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if(String.valueOf(oldLocations.getSelectedItem()).equals("")){
                return new NameDesc(someName.getText(), someDesc.getText());
            } else {
                return new NameDesc("", "", String.valueOf(oldLocations.getSelectedItem()));
            }
        } else {
            return new NameDesc("", "");
        }

    }

    private static class NameDesc {
        String name;
        String desc;
        String activityType;

        public NameDesc(final String text, final String text1) {
            this.name = text;
            this.desc = text1;
        }
        public NameDesc(final String text, final String text1, final String activityType) {
            this.name = text;
            this.desc = text1;
            this.activityType = activityType;
        }
    }

    private static class CustomButton extends JToggleButton{
        String name;
        
        public CustomButton(String name, StretchIcon img) {
            super(img);
            this.name = name;
            this.setPreferredSize(new Dimension(300, 300));
            this.setBackground(Color.BLACK);
            this.setBorder(BorderFactory.createEmptyBorder());
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
