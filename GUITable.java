package myChats;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.awt.image.*;



public class MyChats extends JFrame {//MyQQ 雏形
    public static void main(String[] args) {
        try {
            new MyChats().showTable();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void textSQL()  {
        try {
            initMySQL();
            for( var tie:MySQL.entrySet()){
                System.out.println(tie.getKey()+":"+tie.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private TextField idID=null,keyKEY=null;
    private HashMap<String,String> MySQL= MySQL=new HashMap<String, String>();
    private void initMySQL() throws  FileNotFoundException{

        //io流//把数据从后台加载出来
        File file=new File("mySQL.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            int data=0;//读出错误
            try{//这个算法搞得我好惨
                FileInputStream fis=new FileInputStream(file);
                StringBuilder id=new StringBuilder();
                StringBuilder key=new StringBuilder();
                boolean flag=true;
                do {
                    data = fis.read();
                    if (data == -1) break;
                    if (data != '\n') {
                        if(flag==true){
                            id.append((char)data);
                        }
                        else key.append((char)data);
                    }
                    else{
                        if(flag==true){
                            flag=false;
                        }
                        else{
                            flag=true;
                            MySQL.put(id.toString(),key.toString());
                            id.delete(0,id.length());
                            key.delete(0,key.length());
                        }
                    }
                }while(data!=-1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public MyChats()throws FileNotFoundException{

        //获取面板容器
        this.setTitle("登录GUI");
        Container con=getContentPane();
        con.setLayout(new GridLayout(3,1));


        //图片
        URL res=MyChats.class.getResource("D:\\IdeaProjects\\javalearn\\multithread\\src\\myChats\\myChats\\qq.jpeg");
        Icon imageIcon=new ImageIcon(res);
        JLabel iconLabel=new JLabel();
        iconLabel.setIcon(imageIcon);
        iconLabel.setSize(20,40);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);



        //四个按钮
        con.setName("登录GUI");
        JRadioButton jrb1=new JRadioButton("记住密码" );
        JRadioButton jrb2=new JRadioButton("自动登录");
        JButton login=new JButton("登录");
        JButton init=new JButton("本地注册");//实现四个按钮
        init.setSize(40,20);

        //注册监听
        init.addActionListener(new Register());

        //登录监听
        login.addActionListener(new Login());


        //第一块ID面板
        JPanel pid=new JPanel();
        pid.setLayout(new FlowLayout());
        pid.setBounds(250,100,50,10);

        JLabel lid=new JLabel("ID:");
        TextField ID=new TextField();
        ID.setSize(400,10);
        idID=new TextField(ID.getText());
        pid.add(lid);
        pid.add(ID);


        //第二块KEY面板
        JPanel pkey=new JPanel();
        pkey.setLayout(new FlowLayout());
        pkey.setSize(400,10);

        JLabel lkey=new JLabel("Key:");
        TextField Key=new TextField();
        Key.setEchoChar('*');
        Key.setSize(400,10);
        keyKEY=new TextField(Key.getText().toString());
        pkey.add(lkey);
        pkey.add(Key);


        //按钮面板
        login.setSize(50,20);
        JPanel Jbutton=new JPanel();
        Jbutton.setLayout(new FlowLayout());
        Jbutton.add(jrb1);
        Jbutton.add(jrb2);
        Jbutton.add(login);


        //网页链接
        JButton webLink=new JButton("网页注册");
        webLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = "https://baidu.com";
                    URI uri = URI.create(url);
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(uri);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //底部按钮面板
        JPanel bottom=new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(init);
        bottom.add(webLink);



        //大面板
        JPanel BigPanel=new JPanel();
        BigPanel.setSize(400,200);
        BigPanel.setLayout(new GridLayout(4,1));

        BigPanel.add(pid);
        BigPanel.add(pkey);
        BigPanel.add(Jbutton);
        BigPanel.add(bottom);


        //容器添加
        con.add(iconLabel);
        con.add(BigPanel);


    }

    public void showTable(){
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500,400);
        setResizable(true);//登陆界面关闭后开始网络会话
    }


    //内部类 :登录
    private class Login extends  JFrame implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            try {
                initMySQL();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            String idw=idID.getText().toString();
            if(!MySQL.containsKey(idw)){
                new SimpleDiolag("账户不存在!");
            }
            else{
                String keyw=keyKEY.getText().toString();
                if(MySQL.get(idw)!=keyw){
                    //密码错误 弹窗
                    new SimpleDiolag("密码错误!");
                }
                else{
                    //聊天弹窗
                    new SimpleDiolag("登录成功");
                }
            }
        }
    }


    //内部类 ：注册

    private class Register extends  JFrame implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            Container con=getContentPane();
            con.setLayout(new GridLayout(3,1));

            //第一块ID面板
            JPanel pid=new JPanel();
            pid.setLayout(new FlowLayout());
            pid.setSize(400,10);

            JLabel lid=new JLabel("ID:");
            TextField ID=new TextField("");
            ID.setSize(400,10);
            pid.add(lid);
            pid.add(ID);

            //第二块KEY面板
            JPanel pkey=new JPanel();
            pkey.setLayout(new FlowLayout());
            pkey.setSize(400,10);

            JLabel lkey=new JLabel("Key:");
            TextField Key=new TextField("");
            Key.setEchoChar('*');
            Key.setSize(400,10);
            pkey.add(lkey);
            pkey.add(Key);



            JButton button=new JButton("注册");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        initMySQL();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    String idx=ID.getText();
                    if(MySQL.containsKey(idx)){
                        new SimpleDiolag("用户名已存在!");
                    }
                    else{
                        String keyx=Key.getText();
                        if(keyx.length()<6){
                            new SimpleDiolag("密码过于简单!");
                        }
                        else{
                            try {
                                FileOutputStream fos=new FileOutputStream(new File("mySQL.txt"),true);
                                for(int i=0;i<idx.length();i++){
                                    fos.write((int)idx.charAt(i));
                                }
                                fos.write((int)'\n');
                                for(int i=0;i<keyx.length();i++){
                                    fos.write((int)keyx.charAt(i));
                                }
                                fos.write((int)'\n');
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            new SimpleDiolag("注册成功!");
                        }
                    }
                }
            });
            con.add(pid);
            con.add(pkey);
            con.add(button);
            pack();


        }
    }
}

class SimpleDiolag extends  JFrame{

    public SimpleDiolag(String diolog){

        this.setTitle(diolog);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        add(new JLabel(diolog));

    }
}
