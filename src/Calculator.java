import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Calculator {
    //操作数及各个控制开关的设置
    String str1="0";
    String str2="0";
    String signal="+";
    String result="";
    //开关1用于选择输入方向，将要写入str1或str2
    int k1=1;
    //开关2用于记录符号键的次数，若>1则证明是多个运算数的情况
    int k2=1;
    //开关3用于标识str1是否可以被清零，=1，可以，！=1，不可以
    int k3=1;
    //开关4标识str2是否可以被清零
    int k4=1;
    //开关5用于控制小数点是否可以被录入，=1，可以，！=1，输入的小数点被丢掉
    int k5=1;
    //store的作用类型于寄存器，用于记录是否连续按下符号键
    JButton store;
    @SuppressWarnings("rawtypes")
    Vector vt=new Vector(20,11); //向量类

    //声明UI组件并初始化
    JFrame frame=new JFrame("Calculator");
    JTextField result_TextField = new JTextField(result,20);
    JButton clear_Button = new JButton("clear");
    JButton button0 = new JButton("0");
    JButton button1 = new JButton("1");
    JButton button2 = new JButton("2");
    JButton button3 = new JButton("3");
    JButton button4 = new JButton("4");
    JButton button5 = new JButton("5");
    JButton button6 = new JButton("6");
    JButton button7 = new JButton("7");
    JButton button8 = new JButton("8");
    JButton button9 = new JButton("9");
    JButton button_Dian = new JButton(".");
    JButton button_Jia = new JButton("+");
    JButton button_Jian = new JButton("-");
    JButton button_Cheng = new JButton("×");
    JButton button_Chu = new JButton("÷");
    JButton button_Dengyu = new JButton("=");

    public Calculator(){
        button0.setMnemonic(KeyEvent.VK_0);
        button1.setMnemonic(KeyEvent.VK_1);
        button2.setMnemonic(KeyEvent.VK_2);
        button3.setMnemonic(KeyEvent.VK_3);
        button4.setMnemonic(KeyEvent.VK_4);
        button5.setMnemonic(KeyEvent.VK_5);
        button6.setMnemonic(KeyEvent.VK_6);
        button7.setMnemonic(KeyEvent.VK_7);
        button8.setMnemonic(KeyEvent.VK_8);
        button9.setMnemonic(KeyEvent.VK_9);
        button_Jia.setMnemonic(KeyEvent.VK_PLUS);
        button_Jian.setMnemonic(KeyEvent.VK_MINUS);
        button_Cheng.setMnemonic(KeyEvent.VK_MULTIPLY);
        button_Chu.setMnemonic(KeyEvent.VK_SEPARATOR);
        button_Dian.setMnemonic(KeyEvent.VK_DECIMAL);

        result_TextField.setHorizontalAlignment(JTextField.RIGHT);
        //创建Jpanel来进行布局
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(4,4,5,5));
        pan.add(button7);
        pan.add(button8);
        pan.add(button9);
        pan.add(button_Chu);
        pan.add(button4);
        pan.add(button5);
        pan.add(button6);
        pan.add(button_Cheng);
        pan.add(button1);
        pan.add(button2);
        pan.add(button3);
        pan.add(button_Jian);
        pan.add(button0);
        pan.add(button_Dian);
        pan.add(button_Dengyu);
        pan.add(button_Jia);
        pan.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JPanel pan2 = new JPanel();
        pan2.setLayout(new BorderLayout());
        pan2.add(result_TextField,BorderLayout.WEST);
        pan2.add(clear_Button,BorderLayout.EAST);

        //设置主窗口在屏幕中的位置
        frame.setLocation(300,200);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(pan2,BorderLayout.NORTH);
        frame.getContentPane().add(pan,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        //事件处理程序
        //数字键
        class Listener implements ActionListener{
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e){
                String ss=((JButton) e.getSource()).getText();
                store=(JButton) e.getSource();
                vt.add(store);//？？？
                if (k1==1){
                    if (k3==1){
                        str1="";
                        k5=1;
                    }
                    str1=str1+ss;
                    k3=k3+1;
                    result_TextField.setText(str1);
                }else if (k1==2){
                    if (k4==1){
                        str2="";
                        k5=1;
                    }
                    str2=str2+ss;
                    k4=k4+1;
                    result_TextField.setText(str2);
                }
            }
        }

        //运算符号的处理
        class Listener_signal implements ActionListener{
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e){
                String ss2=((JButton) e.getSource()).getText();
                store=(JButton) e.getSource();
                vt.add(store);
                if (k2==1){
                    k1=2;
                    k5=1;
                    signal=ss2;
                    k2=k2+1;
                }else {
                    int a=vt.size();
                    JButton c=(JButton) vt.get(a-2);
                    if (!(c.getText().equals("+"))
                            &&!(c.getText().equals("-"))
                            &&!(c.getText().equals("×"))
                            &&!(c.getText().equals("÷"))){
                        cal();//????
                        str1=result;
                        k1=2;
                        k5=1;
                        k4=1;
                        signal=ss2;
                    }
                    k2=k2+1;
                }
            }
        }

        //clear逻辑
        class Linsener_clear implements ActionListener{
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
                store=(JButton) e.getSource();
                vt.add(store);
                k5=1;
                k2=1;
                k1=1;
                k3=1;
                k4=1;
                str1="0";
                str2="0";
                signal="";
                result="";
                result_TextField.setText(result);
                vt.clear();
            }
        }

        //等于键的逻辑
        class Linsener_dy implements ActionListener{
            @SuppressWarnings("unchecked")
            @Override
            public void actionPerformed(ActionEvent e) {
            store=(JButton) e.getSource();
            vt.add(store);
            cal();

            k1=1;
            k2=1;
            k3=1;
            k4=1;
            str1=result;
            }
        }

        //小数点的处理（跟数字的处理是一样的）
        class Listener_xiaoshu implements ActionListener{
            @SuppressWarnings("unchecked")
            public void actionPerformed(ActionEvent e){
                store=(JButton) e.getSource();
                vt.add(store);
                if (k5==1){
                    String ss2=((JButton) e.getSource()).getText();
                    if (k1==1){
                        if (k3==1){
                            str1="";
                            k5=1;
                        }
                        str1=str1+ss2;
                        k3=k3+1;
                        result_TextField.setText(str1);
                    }else if (k1==2){
                        if (k4==1){
                            str2="";
                            k5=1;
                        }
                        str2=str2+ss2;
                        k4=k4+1;
                        result_TextField.setText(str2);
                    }
                }
                k5=k5+1;
            }
        }

    //注册各个监听器
        Linsener_dy jt_dy=new Linsener_dy();
        Listener jt=new Listener();
        Linsener_clear jt_clear=new Linsener_clear();
        Listener_signal jt_signal=new Listener_signal();
        Listener_xiaoshu jt_xiaoshu=new Listener_xiaoshu();

        button7.addActionListener(jt);
        button8.addActionListener(jt);
        button9.addActionListener(jt);
        button_Chu.addActionListener(jt_signal);
        button4.addActionListener(jt);
        button5.addActionListener(jt);
        button6.addActionListener(jt);
        button_Cheng.addActionListener(jt_signal);
        button1.addActionListener(jt);
        button2.addActionListener(jt);
        button3.addActionListener(jt);
        button_Jian.addActionListener(jt_signal);
        button0.addActionListener(jt);
        button_Dian.addActionListener(jt_xiaoshu);
        button_Dengyu.addActionListener(jt_dy);
        button_Jia.addActionListener(jt_signal);
        clear_Button.addActionListener(jt_clear);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

    }
    public void cal(){
        double a2;
        double b2;
        String c=signal;
        double result2=0;
        if (c.equals("")){
            result_TextField.setText("请输入运算符");
        }else {
            if (str1.equals(".")){
                str1="0.0";
            }
            if (str2.equals(".")){
                str2="0.0";
            }
            a2=Double.valueOf(str1).doubleValue();
            b2=Double.valueOf(str2).doubleValue();

            if (c.equals("+")){
                result2=a2+b2;
            }
            if (c.equals("-")){
                result2=a2-b2;
            }
            if (c.equals("×")){
                result2=a2*b2;
            }
            if (c.equals("÷")){
                if (b2==0){
                    result2=0;
                }else
                    {result2=a2/b2;}
            }
        }
        result=((new Double(result2)).toString());
        result_TextField.setText(result);
    }
    @SuppressWarnings("unsued")
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Calculator cal=new Calculator();
    }
}



