
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sonhy
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    String num1 = null;
    String num2 = null;
    String result = null;
    String operator = null;
    String angleUnit = "Degrees";
    String base = "Dec";
    int intBase = 10;
        
    private boolean isScientificNotation = false;
    private List<Double> dataList = new ArrayList<>();
    private boolean isStatisticsMode = false;
        
    double memoryValue = 0.0;
    
    public MainFrame() {
        initComponents();
    }
    
    private void numberClick(String number) {
        if(isOperatorExists()) {
            //연산자 있음 > num2
            if(num2 != null) {
                num2 += number;
                lblResultPrint(true);
            } else {
                num2 = number;
                lblResultPrint(true);
            }
        } else {
            //연산자 없음 > num1
            if(result != null) {
                num1 = number;
                num2 = null;
                operator = null;
                result = null;
                lblResultPrint(false);
            } else if(num1 != null) {
                //숫자가 이미 있음
                num1 += number;
                lblResultPrint(false);
            } else {
                //숫자가 없음
                num1 = number;
                lblResultPrint(false);
            }
        }
    }
    
    private void operatorClick(String op) {
        operator = op;
        if(num1 != null) {
            lblResult.setText(num1 + operator);
        }
    }
    
    private boolean isOperatorExists() {
        if(operator != null) {
            return true; //연산자 있음
        } else {
            return false; //연산자 없음
        }
    }
    
    private void lblResultPrint(boolean b) {
        if(b) {
            lblResult.setText(num1 + operator + num2);
        } else {
            lblResult.setText(num1);
        }
    }
    
    private void valueSet() {
        num1 = result;
        num2 = null;
        operator = null;
    }
    
    private void trigOperator(String trigonometric) {
        double value = Double.parseDouble(lblResult.getText());
        double resultValue = 0.0;
        
        //쌍곡선함수와 역쌍곡선 함수 : 입력 값의 단위 = 실수 > 각도 변환 x
        //역함수, 삼각함수 : 입력 값의 단위 = 라디안 > 각도 변환 o
                
        if(chkHyp.isSelected()) {
            //true > 쌍곡선, 역쌍곡선
            if(chkInv.isSelected()) {
                //true : Hyp+Inv > 역쌍곡선 함수
                switch(trigonometric) {
                    case "sinh⁻¹":
                        resultValue = Math.log(value + Math.sqrt(Math.pow(value, 2) + 1));
                        break;
                    case "cosh⁻¹":
                        if(value<1) {
                            lblResult.setText("1보다 큰 값을 입력하세요.");
                            return;
                        } else {
                            resultValue = Math.log(value + Math.sqrt(Math.pow(value, 2) - 1));
                            break;
                        }
                    case "tanh⁻¹":
                        if(value > 1 || value < -1) {
                            lblResult.setText("-1 ~ 1 사이의 값을 입력하세요.");
                            return;
                        } else {
                            resultValue = 0.5 * Math.log((1 + value) / (1 - value));
                            break;
                        }
                }
                
            } else {
                //false : Hyp > 쌍곡선 함수
                switch(trigonometric) {
                    case "sinh":
                        resultValue = Math.sinh(value);
                        break;
                    case "cosh":
                        resultValue = Math.cosh(value);
                        break;
                    case "tanh":
                        resultValue = Math.tanh(value);
                        break;
                }
            }
            
        } else {
            //false > 역함수, 기본함수            
            if(chkInv.isSelected()) {
                //true : Inv > 역함수
                switch(trigonometric) {
                    case "sin⁻¹":
                        resultValue = Math.asin(value);
                        break;
                    case "cos⁻¹":
                        resultValue = Math.acos(value);
                        break;
                    case "tan⁻¹":
                        resultValue = Math.atan(value);
                        break;
                }
            } else {
                //false : 선택 x > 삼각함수
                double radianValue;
                
                if(rbtnDegrees.isSelected()) {
                    radianValue = Math.toRadians(value);
                } else if(rbtnRadians.isSelected()) {
                    radianValue = value;
                } else if(rbtnGrads.isSelected()) {
                    //그라디안 -> 라디안 변환 = grads * (PI / 200)
                    radianValue = value * (Math.PI / 200.0);
                } else {
                    radianValue = value;
                }
                
                switch(trigonometric) {
                    case "sin":
                        resultValue = Math.sin(radianValue);
                        break;
                    case "cos":
                        resultValue = Math.cos(radianValue);
                        break;
                    case "tan":
                        resultValue = Math.tan(radianValue);
                        break;
                }
            }
        }
        lblResult.setText(trigonometric+"("+lblResult.getText()+") = " + String.valueOf(resultValue));
        valueSet();
    }
    
    private void changeBase(String radix) {
        int value = Integer.parseInt(lblResult.getText(), intBase);
        
        switch(radix) {
            case "Hex": //16진법
                intBase = 16;
                result = String.valueOf(Integer.toHexString(value)).toUpperCase();
                break;
            case "Dec": //10진법
                intBase = 10;
                result = String.valueOf(value);
                break;
            case "Oct": //8진법
                intBase = 8;
                result = String.valueOf(Integer.toOctalString(value));
                break;
            case "Bin": //2진법
                intBase = 2;
                result = String.valueOf(Integer.toBinaryString(value)); 
                break;
            default:
                intBase = 10;
                result = String.valueOf(value);
                break;
        }
        lblResult.setText(result);
    }
    
    private void changeTrigBtn() {
        if(chkHyp.isSelected() && chkInv.isSelected()) {
            btnSin.setText("sinh⁻¹");
            btnCos.setText("cosh⁻¹");
            btnTan.setText("tanh⁻¹");
        } else if(chkHyp.isSelected()) {
            btnSin.setText("sinh");
            btnCos.setText("cosh");
            btnTan.setText("tanh");
        } else if(chkInv.isSelected()) {
            btnSin.setText("sin⁻¹");
            btnCos.setText("cos⁻¹");
            btnTan.setText("tan⁻¹");
        } else {
            btnSin.setText("sin");
            btnCos.setText("cos");
            btnTan.setText("tan");
        }
    }
    
    private void convertToDMS(double decimalDegrees) {
        if(decimalDegrees < 0) {
            lblResult.setText("양수를 입력하세요.");
            return;
        }
        
        //도(degrees)
        int degrees = (int)decimalDegrees;
        
        //분
        double decimalMinutes = (decimalDegrees - degrees) * 60;
        int minutes = (int)decimalMinutes;
        
        //초
        double seconds = (decimalMinutes - minutes) * 60;
        seconds = Math.round(seconds * 1000.0) / 1000.0;
        
        lblResult.setText(String.valueOf(degrees + "° " + minutes + "' " + seconds + "\""));
    }
    
    private void convertToDecimal(String dmsString) {
        try {
            String[] parts = dmsString.split("[° ' \"]+");
            double degrees = Double.parseDouble(parts[0]);
            double minutes = Double.parseDouble(parts[1]);
            double seconds = Double.parseDouble(parts[2]);
            
            lblResult.setText(String.valueOf(degrees + (minutes/60) + (seconds/3600)));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            lblResult.setText("DMS 형식 오류");
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblResult = new javax.swing.JLabel();
        btnCE = new javax.swing.JButton();
        btnBackspace = new javax.swing.JButton();
        btnMod = new javax.swing.JButton();
        btnOr = new javax.swing.JButton();
        btnLsh = new javax.swing.JButton();
        btnResult = new javax.swing.JButton();
        btnSum = new javax.swing.JButton();
        btnDiv = new javax.swing.JButton();
        btnMul = new javax.swing.JButton();
        btnSub = new javax.swing.JButton();
        btnDot = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btnChangeSign = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btnMC = new javax.swing.JButton();
        btnMR = new javax.swing.JButton();
        btnMS = new javax.swing.JButton();
        btnM = new javax.swing.JButton();
        btnAnd = new javax.swing.JButton();
        btnXor = new javax.swing.JButton();
        btnNot = new javax.swing.JButton();
        btnPi = new javax.swing.JButton();
        btnCos = new javax.swing.JButton();
        btnFE = new javax.swing.JButton();
        btnDms = new javax.swing.JButton();
        btnSin = new javax.swing.JButton();
        btnFactorial = new javax.swing.JButton();
        btnCloseParen = new javax.swing.JButton();
        btnLn = new javax.swing.JButton();
        btnLog = new javax.swing.JButton();
        btnCube = new javax.swing.JButton();
        btnOpenParen = new javax.swing.JButton();
        btnExp = new javax.swing.JButton();
        btnXY = new javax.swing.JButton();
        btnSquare = new javax.swing.JButton();
        btnTan = new javax.swing.JButton();
        btnReciprocal = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        rbtnDegrees = new javax.swing.JRadioButton();
        rbtnRadians = new javax.swing.JRadioButton();
        rbtnGrads = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        rbtnHex = new javax.swing.JRadioButton();
        rbtnDec = new javax.swing.JRadioButton();
        rbtnOct = new javax.swing.JRadioButton();
        rbtnBin = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        chkInv = new javax.swing.JCheckBox();
        chkHyp = new javax.swing.JCheckBox();
        tbtnSta = new javax.swing.JToggleButton();
        tbtnAve = new javax.swing.JToggleButton();
        tbtnSum = new javax.swing.JToggleButton();
        tbtnS = new javax.swing.JToggleButton();
        tbtnDat = new javax.swing.JToggleButton();
        btnA = new javax.swing.JToggleButton();
        btnC = new javax.swing.JToggleButton();
        btnD = new javax.swing.JToggleButton();
        btnE = new javax.swing.JToggleButton();
        btnF = new javax.swing.JToggleButton();
        btnClear = new javax.swing.JButton();
        btnB = new javax.swing.JToggleButton();
        btn0 = new javax.swing.JButton();
        btnInt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblResult.setBackground(new java.awt.Color(255, 255, 255));
        lblResult.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblResult.setToolTipText("");
        lblResult.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnCE.setForeground(new java.awt.Color(255, 51, 51));
        btnCE.setText("CE");
        btnCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCEActionPerformed(evt);
            }
        });

        btnBackspace.setForeground(new java.awt.Color(255, 51, 51));
        btnBackspace.setText("Backspace");
        btnBackspace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackspaceActionPerformed(evt);
            }
        });

        btnMod.setForeground(new java.awt.Color(255, 51, 51));
        btnMod.setText("Mod");
        btnMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModActionPerformed(evt);
            }
        });

        btnOr.setForeground(new java.awt.Color(255, 51, 51));
        btnOr.setText("Or");
        btnOr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrActionPerformed(evt);
            }
        });

        btnLsh.setForeground(new java.awt.Color(255, 51, 51));
        btnLsh.setText("Lsh");
        btnLsh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLshActionPerformed(evt);
            }
        });

        btnResult.setForeground(new java.awt.Color(255, 51, 51));
        btnResult.setText("=");
        btnResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResultActionPerformed(evt);
            }
        });

        btnSum.setForeground(new java.awt.Color(255, 51, 51));
        btnSum.setText("+");
        btnSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumActionPerformed(evt);
            }
        });

        btnDiv.setForeground(new java.awt.Color(255, 51, 51));
        btnDiv.setText("/");
        btnDiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivActionPerformed(evt);
            }
        });

        btnMul.setForeground(new java.awt.Color(255, 51, 51));
        btnMul.setText("*");
        btnMul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMulActionPerformed(evt);
            }
        });

        btnSub.setForeground(new java.awt.Color(255, 51, 51));
        btnSub.setText("-");
        btnSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubActionPerformed(evt);
            }
        });

        btnDot.setForeground(new java.awt.Color(0, 0, 255));
        btnDot.setText(".");
        btnDot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDotActionPerformed(evt);
            }
        });

        btn9.setForeground(new java.awt.Color(0, 0, 255));
        btn9.setText("9");
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btn6.setForeground(new java.awt.Color(0, 0, 255));
        btn6.setText("6");
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btn3.setForeground(new java.awt.Color(0, 0, 255));
        btn3.setText("3");
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btnChangeSign.setForeground(new java.awt.Color(0, 0, 255));
        btnChangeSign.setText("+/-");
        btnChangeSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeSignActionPerformed(evt);
            }
        });

        btn8.setForeground(new java.awt.Color(0, 0, 255));
        btn8.setText("8");
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn5.setForeground(new java.awt.Color(0, 0, 255));
        btn5.setText("5");
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btn2.setForeground(new java.awt.Color(0, 0, 255));
        btn2.setText("2");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn7.setForeground(new java.awt.Color(0, 0, 255));
        btn7.setText("7");
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn4.setForeground(new java.awt.Color(0, 0, 255));
        btn4.setText("4");
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn1.setForeground(new java.awt.Color(0, 0, 255));
        btn1.setText("1");
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btnMC.setForeground(new java.awt.Color(255, 51, 51));
        btnMC.setText("MC");
        btnMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMCActionPerformed(evt);
            }
        });

        btnMR.setForeground(new java.awt.Color(255, 51, 51));
        btnMR.setText("MR");
        btnMR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMRActionPerformed(evt);
            }
        });

        btnMS.setForeground(new java.awt.Color(255, 51, 51));
        btnMS.setText("MS");
        btnMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMSActionPerformed(evt);
            }
        });

        btnM.setForeground(new java.awt.Color(255, 51, 51));
        btnM.setText("M+");
        btnM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMActionPerformed(evt);
            }
        });

        btnAnd.setForeground(new java.awt.Color(255, 51, 51));
        btnAnd.setText("And");
        btnAnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAndActionPerformed(evt);
            }
        });

        btnXor.setForeground(new java.awt.Color(255, 51, 51));
        btnXor.setText("Xor");
        btnXor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXorActionPerformed(evt);
            }
        });

        btnNot.setForeground(new java.awt.Color(255, 51, 51));
        btnNot.setText("Not");
        btnNot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotActionPerformed(evt);
            }
        });

        btnPi.setForeground(new java.awt.Color(0, 0, 255));
        btnPi.setText("pi");
        btnPi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiActionPerformed(evt);
            }
        });

        btnCos.setForeground(new java.awt.Color(255, 51, 255));
        btnCos.setText("cos");
        btnCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCosActionPerformed(evt);
            }
        });

        btnFE.setForeground(new java.awt.Color(255, 51, 255));
        btnFE.setText("F-E");
        btnFE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFEActionPerformed(evt);
            }
        });

        btnDms.setForeground(new java.awt.Color(255, 51, 255));
        btnDms.setText("dms");
        btnDms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDmsActionPerformed(evt);
            }
        });

        btnSin.setForeground(new java.awt.Color(255, 51, 255));
        btnSin.setText("sin");
        btnSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinActionPerformed(evt);
            }
        });

        btnFactorial.setForeground(new java.awt.Color(255, 51, 255));
        btnFactorial.setText("n!");
        btnFactorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFactorialActionPerformed(evt);
            }
        });

        btnCloseParen.setForeground(new java.awt.Color(255, 51, 255));
        btnCloseParen.setText(")");
        btnCloseParen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseParenActionPerformed(evt);
            }
        });

        btnLn.setForeground(new java.awt.Color(255, 51, 255));
        btnLn.setText("ln");
        btnLn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLnActionPerformed(evt);
            }
        });

        btnLog.setForeground(new java.awt.Color(255, 51, 255));
        btnLog.setText("log");
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        btnCube.setForeground(new java.awt.Color(255, 51, 255));
        btnCube.setText("x^3");
        btnCube.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCubeActionPerformed(evt);
            }
        });

        btnOpenParen.setForeground(new java.awt.Color(255, 51, 255));
        btnOpenParen.setText("(");
        btnOpenParen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenParenActionPerformed(evt);
            }
        });

        btnExp.setForeground(new java.awt.Color(255, 51, 255));
        btnExp.setText("Exp");
        btnExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpActionPerformed(evt);
            }
        });

        btnXY.setForeground(new java.awt.Color(255, 51, 255));
        btnXY.setText("x^y");
        btnXY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXYActionPerformed(evt);
            }
        });

        btnSquare.setForeground(new java.awt.Color(255, 51, 255));
        btnSquare.setText("x^2");
        btnSquare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSquareActionPerformed(evt);
            }
        });

        btnTan.setForeground(new java.awt.Color(255, 51, 255));
        btnTan.setText("tan");
        btnTan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTanActionPerformed(evt);
            }
        });

        btnReciprocal.setForeground(new java.awt.Color(255, 51, 255));
        btnReciprocal.setText("1/x");
        btnReciprocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReciprocalActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup2.add(rbtnDegrees);
        rbtnDegrees.setSelected(true);
        rbtnDegrees.setText("Degrees");
        rbtnDegrees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDegreesActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtnRadians);
        rbtnRadians.setText("Radians");
        rbtnRadians.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnRadiansActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtnGrads);
        rbtnGrads.setText("Grads");
        rbtnGrads.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnGradsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rbtnDegrees)
                .addGap(87, 87, 87)
                .addComponent(rbtnRadians)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(rbtnGrads)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnDegrees)
                    .addComponent(rbtnRadians)
                    .addComponent(rbtnGrads))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buttonGroup1.add(rbtnHex);
        rbtnHex.setText("Hex");
        rbtnHex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnHexActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnDec);
        rbtnDec.setSelected(true);
        rbtnDec.setText("Dec");
        rbtnDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDecActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnOct);
        rbtnOct.setText("Oct");
        rbtnOct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnOctActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnBin);
        rbtnBin.setText("Bin");
        rbtnBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(rbtnHex)
                .addGap(32, 32, 32)
                .addComponent(rbtnDec, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(rbtnOct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(rbtnBin)
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnHex)
                    .addComponent(rbtnDec)
                    .addComponent(rbtnOct)
                    .addComponent(rbtnBin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chkInv.setText("Inv");
        chkInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInvActionPerformed(evt);
            }
        });

        chkHyp.setText("Hyp");
        chkHyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHypActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(chkInv, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkHyp, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkHyp)
                    .addComponent(chkInv))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbtnSta.setForeground(new java.awt.Color(0, 0, 255));
        tbtnSta.setText("Sta");
        tbtnSta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnStaActionPerformed(evt);
            }
        });

        tbtnAve.setForeground(new java.awt.Color(0, 0, 255));
        tbtnAve.setText("Ave");
        tbtnAve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnAveActionPerformed(evt);
            }
        });

        tbtnSum.setForeground(new java.awt.Color(0, 0, 255));
        tbtnSum.setText("Sum");
        tbtnSum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnSumActionPerformed(evt);
            }
        });

        tbtnS.setForeground(new java.awt.Color(0, 0, 255));
        tbtnS.setText("s");
        tbtnS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnSActionPerformed(evt);
            }
        });

        tbtnDat.setForeground(new java.awt.Color(0, 0, 255));
        tbtnDat.setText("Dat");
        tbtnDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnDatActionPerformed(evt);
            }
        });

        btnA.setText("A");
        btnA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAActionPerformed(evt);
            }
        });

        btnC.setText("C");
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });

        btnD.setText("D");
        btnD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDActionPerformed(evt);
            }
        });

        btnE.setText("E");
        btnE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEActionPerformed(evt);
            }
        });

        btnF.setText("F");
        btnF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFActionPerformed(evt);
            }
        });

        btnClear.setForeground(new java.awt.Color(255, 51, 51));
        btnClear.setText("C");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnB.setText("B");
        btnB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBActionPerformed(evt);
            }
        });

        btn0.setForeground(new java.awt.Color(0, 0, 255));
        btn0.setText("0");
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });

        btnInt.setForeground(new java.awt.Color(255, 51, 51));
        btnInt.setText("Int");
        btnInt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tbtnDat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnTan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSquare, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnReciprocal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(tbtnSta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tbtnAve, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tbtnSum, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tbtnS, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnCos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnSin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnDms, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnFE, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnXY, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnExp, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnOpenParen, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnCube, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnFactorial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnLog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnLn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnCloseParen, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnMR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnM, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btn4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btn7, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btnA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnChangeSign, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnDot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSum, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btnSub, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btnMul, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btnDiv, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                    .addComponent(btnD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnResult, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLsh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnOr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMod, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnE, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnNot, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXor, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAnd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnInt, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(btnF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblResult, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBackspace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCE, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblResult, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBackspace)
                            .addComponent(btnCE)
                            .addComponent(btnClear))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnMC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMR)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnM)
                                    .addComponent(btn0)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnCloseParen)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnLn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnLog)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnFactorial))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnOpenParen)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnExp)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnXY)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCube))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnFE)
                                        .addComponent(tbtnSta))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDms)
                                        .addComponent(tbtnAve))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnSin)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnCos))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(tbtnSum)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tbtnS))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnPi)
                                .addComponent(btnA)
                                .addComponent(btnC)
                                .addComponent(btnD)
                                .addComponent(btnE)
                                .addComponent(btnF)
                                .addComponent(btnB))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnReciprocal)
                                .addComponent(btnSquare)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnTan)
                                    .addComponent(tbtnDat)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLsh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnResult)
                            .addComponent(btnInt)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDiv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMul)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSub)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSum))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDot))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChangeSign))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNot)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
        numberClick(btn0.getText());
    }//GEN-LAST:event_btn0ActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        numberClick(btn1.getText());
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        numberClick(btn2.getText());
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        numberClick(btn3.getText());
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        numberClick(btn4.getText());
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        numberClick(btn5.getText());
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        numberClick(btn6.getText());
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        numberClick(btn7.getText());
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        numberClick(btn8.getText());
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        numberClick(btn9.getText());
    }//GEN-LAST:event_btn9ActionPerformed

    private void btnDivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivActionPerformed
        operatorClick(btnDiv.getText());
    }//GEN-LAST:event_btnDivActionPerformed

    private void btnMulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMulActionPerformed
        operatorClick(btnMul.getText());
    }//GEN-LAST:event_btnMulActionPerformed

    private void btnSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubActionPerformed
        operatorClick(btnSub.getText());
    }//GEN-LAST:event_btnSubActionPerformed

    private void btnSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumActionPerformed
        operatorClick(btnSum.getText());
    }//GEN-LAST:event_btnSumActionPerformed

    private void btnResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResultActionPerformed
        if(num1 != null && num2 != null) {            
            switch(operator) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                case "Mod":
                    double dbNum1 = Double.parseDouble(num1);
                    double dbNum2 = Double.parseDouble(num2);
                    
                    switch(operator) {
                        case "+": result = String.valueOf(dbNum1 + dbNum2); break;
                        case "-": result = String.valueOf(dbNum1 - dbNum2); break;
                        case "*": result = String.valueOf(dbNum1 * dbNum2); break;
                        case "^": result = String.valueOf(Math.pow(dbNum1, dbNum2)); break;
                        case "/": 
                            if(dbNum2 == 0) {
                                lblResult.setText("0으로 나눌 수 없습니다.");
                                valueSet();
                                return;
                            }
                            result = String.valueOf(dbNum1 / dbNum2);
                            break;
                        case "Mod": result = String.valueOf(dbNum1 % dbNum2); break;
                    }
                    break;
                   
                case "And":
                case "Or":
                case "Xor":
                case "Lsh":
                    int intNum1 = Integer.parseInt(num1);
                    int intNum2 = Integer.parseInt(num2);
                    switch(operator) {
                        case "And": result = String.valueOf(intNum1 & intNum2); break;
                        case "Or": result = String.valueOf(intNum1 | intNum2); break;
                        case "Xor": result = String.valueOf(intNum1 ^ intNum2); break;
                        case "Lsh": result = String.valueOf(intNum1 << intNum2); break;
                    }
            }
            if(result != null & result.endsWith(".0")) {
                result = result.substring(0, result.length()-2);
            }
        }
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnResultActionPerformed

    private void btnBackspaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackspaceActionPerformed
        String currentText = lblResult.getText();
        
        if(!currentText.equals("") && currentText.length() > 1) {
            if(isOperatorExists()) {
                num2 = num2.substring(0, num2.length()-1);
                lblResultPrint(true);
            } else {
                num1 = num1.substring(0, num1.length()-1);
                lblResultPrint(false);
            }
        } else if(currentText.length() == 1) {
            num1 = "0";
            lblResultPrint(false);
        }
    }//GEN-LAST:event_btnBackspaceActionPerformed

    private void btnCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCEActionPerformed
        lblResult.setText("");
        num1 = null;
        num2 = null;
        result = null;
        operator = null;
    }//GEN-LAST:event_btnCEActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        if(num1 != null && num2 != null) {
            num2 = null;
            lblResult.setText(num1 + operator);
        } else if(num2 == null) {
            num1 = null;
            lblResult.setText("");
        }
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnChangeSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeSignActionPerformed
        if(isOperatorExists()) {
            double value = Double.parseDouble(num2);
            num2 = String.valueOf(value * -1);
            lblResultPrint(true);
        } else {
            double value = Double.parseDouble(num1);
            num1 = String.valueOf(value * -1);
            lblResultPrint(false);
        }
    }//GEN-LAST:event_btnChangeSignActionPerformed

    private void btnModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModActionPerformed
        operatorClick(btnMod.getText());
    }//GEN-LAST:event_btnModActionPerformed

    private void btnAndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAndActionPerformed
        operatorClick(btnAnd.getText());
    }//GEN-LAST:event_btnAndActionPerformed

    private void btnOrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrActionPerformed
        operatorClick(btnOr.getText());
    }//GEN-LAST:event_btnOrActionPerformed

    private void btnXorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXorActionPerformed
        operatorClick(btnXor.getText());
    }//GEN-LAST:event_btnXorActionPerformed

    private void btnNotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotActionPerformed
        int intNum1 =  Integer.parseInt(num1);
        lblResult.setText(String.valueOf(~intNum1));
    }//GEN-LAST:event_btnNotActionPerformed

    private void btnLshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLshActionPerformed
        operatorClick(btnLsh.getText());
    }//GEN-LAST:event_btnLshActionPerformed

    private void btnIntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntActionPerformed
        double num = Double.parseDouble(lblResult.getText());
        lblResult.setText(String.valueOf((int)num));
    }//GEN-LAST:event_btnIntActionPerformed

    private void rbtnDegreesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDegreesActionPerformed
        angleUnit = "Degrees";
    }//GEN-LAST:event_rbtnDegreesActionPerformed

    private void rbtnRadiansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnRadiansActionPerformed
        angleUnit = "Radians";
    }//GEN-LAST:event_rbtnRadiansActionPerformed

    private void rbtnGradsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnGradsActionPerformed
        angleUnit = "Grads";
    }//GEN-LAST:event_rbtnGradsActionPerformed

    private void btnSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinActionPerformed
        trigOperator(btnSin.getText());
    }//GEN-LAST:event_btnSinActionPerformed

    private void btnCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCosActionPerformed
        trigOperator(btnCos.getText());
    }//GEN-LAST:event_btnCosActionPerformed

    private void btnTanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTanActionPerformed
        trigOperator(btnTan.getText());
    }//GEN-LAST:event_btnTanActionPerformed

    private void rbtnHexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnHexActionPerformed
        base = "Hex";
        changeBase(base);
    }//GEN-LAST:event_rbtnHexActionPerformed

    private void rbtnDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDecActionPerformed
        base = "Dec";
        changeBase(base);
    }//GEN-LAST:event_rbtnDecActionPerformed

    private void rbtnOctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnOctActionPerformed
        base = "Oct";
        changeBase(base);
    }//GEN-LAST:event_rbtnOctActionPerformed

    private void rbtnBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBinActionPerformed
        base = "Bin";
        changeBase(base);
    }//GEN-LAST:event_rbtnBinActionPerformed

    private void chkInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInvActionPerformed
        changeTrigBtn();
    }//GEN-LAST:event_chkInvActionPerformed

    private void chkHypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHypActionPerformed
        changeTrigBtn();
    }//GEN-LAST:event_chkHypActionPerformed

    private void btnDmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDmsActionPerformed
        String currentText = lblResult.getText();
        if(currentText.contains("°")) {
            //현재 DMS 형식 -> 10진수 형식으로 변환
            convertToDecimal(currentText);
        } else {
            //현재 10진수 형식 -> DMS 형식으로 변환
            double value = Double.parseDouble(currentText);
            convertToDMS(value);
        }
    }//GEN-LAST:event_btnDmsActionPerformed

    private void btnFEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFEActionPerformed
        try {
            double value = Double.parseDouble(lblResult.getText());
            
            if(isScientificNotation) {
                //공학용 표기법 -> 고정 소수점 표기법으로 변환
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.##############");
                lblResult.setText(df.format(value));
                isScientificNotation = false;
            } else {
                //고정 소수점 표기법 -> 공학용 표기법으로 변환
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.######E0");
                lblResult.setText(df.format(value));
                isScientificNotation = true;
            }
        } catch(NumberFormatException e) {
            lblResult.setText("유효한 숫자가 아닙니다.");
        }
    }//GEN-LAST:event_btnFEActionPerformed

    private void tbtnStaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnStaActionPerformed
        isStatisticsMode = !isStatisticsMode;
        dataList.clear();
        
        if(isStatisticsMode) {
            lblResult.setText("STAT 모드"); //통계 모드
        } else {
            lblResult.setText("");
        }
    }//GEN-LAST:event_tbtnStaActionPerformed

    private void tbtnDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnDatActionPerformed
        if(isStatisticsMode) {
            try {
                double value = Double.parseDouble(lblResult.getText());
                dataList.add(value);
                lblResult.setText("");
            } catch (NumberFormatException e) {
                lblResult.setText("유효한 숫자가 아닙니다.");
            }
        } else {
            lblResult.setText("STAT 모드를 활성화하세요.");
        }
    }//GEN-LAST:event_tbtnDatActionPerformed

    private void tbtnAveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnAveActionPerformed
        if(isStatisticsMode && !dataList.isEmpty()) {
            double sum = 0;
            for(double data : dataList) {
                sum += data;
            }
            double average = sum / dataList.size();
            lblResult.setText(String.valueOf(average));
        } else if(isStatisticsMode && dataList.isEmpty()) {
            lblResult.setText("데이터가 없습니다.");
        } else {
            lblResult.setText("STAT 모드를 활성화하세요.");
        }
    }//GEN-LAST:event_tbtnAveActionPerformed

    private void tbtnSumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnSumActionPerformed
        if(isStatisticsMode && !dataList.isEmpty()) {
            double sum = 0;
            for(double data : dataList) {
                sum += data;
            }
            lblResult.setText(String.valueOf(sum));
        } else if(isStatisticsMode && dataList.isEmpty()) {
            lblResult.setText("데이터가 없습니다.");
        } else {
            lblResult.setText("STAT 모드를 활성화하세요.");
        }
    }//GEN-LAST:event_tbtnSumActionPerformed

    private void tbtnSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnSActionPerformed
        //표준편차 계산
        if(isStatisticsMode && dataList.size() > 1) {
            double sum = 0;
            for(double data : dataList) {
                sum += data;
            }
            double average = sum / dataList.size();
            
            double sumOfSquares = 0;
            for(double data : dataList) {
                sumOfSquares += Math.pow(data - average, 2);
            }
            
            double standardDeviation = Math.sqrt(sumOfSquares / (dataList.size() - 1));
            lblResult.setText(String.valueOf(standardDeviation));
        } else if(isStatisticsMode && dataList.isEmpty()) {
            lblResult.setText("데이터가 부족합니다.");
        } else {
            lblResult.setText("STAT 모드를 활성화하세요.");
        }
    }//GEN-LAST:event_tbtnSActionPerformed

    private void btnExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpActionPerformed
        //EXP = 10^x
        double value = Double.parseDouble(lblResult.getText());
        result = String.valueOf(Math.pow(10, value));
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnExpActionPerformed

    private void btnLnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLnActionPerformed
        //LN = 자연 로그
        double value = Double.parseDouble(lblResult.getText());
        if(value <= 0) {
            lblResult.setText("0보다 큰 수를 입력하세요.");
            return;
        }
        
        result = String.valueOf(Math.log(value));
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnLnActionPerformed

    private void btnXYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXYActionPerformed
        operatorClick("^");
    }//GEN-LAST:event_btnXYActionPerformed

    private void btnCubeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCubeActionPerformed
        //x^3
        double value = Double.parseDouble(lblResult.getText());
                
        result = String.valueOf(Math.pow(value, 3));
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnCubeActionPerformed

    private void btnSquareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSquareActionPerformed
        //x^2
        double value = Double.parseDouble(lblResult.getText());
                
        result = String.valueOf(Math.pow(value, 2));
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnSquareActionPerformed

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogActionPerformed
        //LOG = 상용 로그
        double value = Double.parseDouble(lblResult.getText());
        if(value <= 0) {
            lblResult.setText("0보다 큰 수를 입력하세요.");
            return;
        }
        
        result = String.valueOf(Math.log10(value));
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnLogActionPerformed

    private void btnFactorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFactorialActionPerformed
        int value = Integer.parseInt(lblResult.getText());
        if(value < 0) {
            lblResult.setText("양수를 입력하세요.");
            return;
        }
        
        long factorial = 1;
        for(int i=1; i<=value; i++) {
            factorial *= i;
        }
        
        result = String.valueOf(factorial);
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnFactorialActionPerformed

    private void btnReciprocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReciprocalActionPerformed
        //1/x
        double value = Integer.parseInt(lblResult.getText());
        if(value == 0) {
            lblResult.setText("0으로 나눌 수 없습니다.");
            return;
        }
        
        result = String.valueOf(1/value);
        lblResult.setText(result);
        valueSet();
    }//GEN-LAST:event_btnReciprocalActionPerformed

    private void btnOpenParenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenParenActionPerformed
        lblResult.setText(lblResult.getText() + "(");
    }//GEN-LAST:event_btnOpenParenActionPerformed

    private void btnCloseParenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseParenActionPerformed
        lblResult.setText(lblResult.getText() + ")");
    }//GEN-LAST:event_btnCloseParenActionPerformed

    private void btnMCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMCActionPerformed
        //memory clear
        memoryValue = 0.0;
    }//GEN-LAST:event_btnMCActionPerformed

    private void btnMRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMRActionPerformed
        //memory recall
        lblResult.setText(String.valueOf(memoryValue));
    }//GEN-LAST:event_btnMRActionPerformed

    private void btnMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMSActionPerformed
        //memory store
        memoryValue = Double.parseDouble(lblResult.getText());
    }//GEN-LAST:event_btnMSActionPerformed

    private void btnMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMActionPerformed
        //memory plus
        double currentValue = Double.parseDouble(lblResult.getText());
        memoryValue += currentValue;
    }//GEN-LAST:event_btnMActionPerformed

    private void btnPiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiActionPerformed
        numberClick(String.valueOf(Math.PI));
    }//GEN-LAST:event_btnPiActionPerformed

    private void btnAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAActionPerformed
        numberClick(btnA.getText());
    }//GEN-LAST:event_btnAActionPerformed

    private void btnBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBActionPerformed
        numberClick(btnB.getText());
    }//GEN-LAST:event_btnBActionPerformed

    private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
        numberClick(btnC.getText());
    }//GEN-LAST:event_btnCActionPerformed

    private void btnDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDActionPerformed
        numberClick(btnD.getText());
    }//GEN-LAST:event_btnDActionPerformed

    private void btnEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEActionPerformed
        numberClick(btnE.getText());
    }//GEN-LAST:event_btnEActionPerformed

    private void btnFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFActionPerformed
        numberClick(btnF.getText());
    }//GEN-LAST:event_btnFActionPerformed

    private void btnDotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDotActionPerformed
        numberClick(btnDot.getText());
    }//GEN-LAST:event_btnDotActionPerformed
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JToggleButton btnA;
    private javax.swing.JButton btnAnd;
    private javax.swing.JToggleButton btnB;
    private javax.swing.JButton btnBackspace;
    private javax.swing.JToggleButton btnC;
    private javax.swing.JButton btnCE;
    private javax.swing.JButton btnChangeSign;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCloseParen;
    private javax.swing.JButton btnCos;
    private javax.swing.JButton btnCube;
    private javax.swing.JToggleButton btnD;
    private javax.swing.JButton btnDiv;
    private javax.swing.JButton btnDms;
    private javax.swing.JButton btnDot;
    private javax.swing.JToggleButton btnE;
    private javax.swing.JButton btnExp;
    private javax.swing.JToggleButton btnF;
    private javax.swing.JButton btnFE;
    private javax.swing.JButton btnFactorial;
    private javax.swing.JButton btnInt;
    private javax.swing.JButton btnLn;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnLsh;
    private javax.swing.JButton btnM;
    private javax.swing.JButton btnMC;
    private javax.swing.JButton btnMR;
    private javax.swing.JButton btnMS;
    private javax.swing.JButton btnMod;
    private javax.swing.JButton btnMul;
    private javax.swing.JButton btnNot;
    private javax.swing.JButton btnOpenParen;
    private javax.swing.JButton btnOr;
    private javax.swing.JButton btnPi;
    private javax.swing.JButton btnReciprocal;
    private javax.swing.JButton btnResult;
    private javax.swing.JButton btnSin;
    private javax.swing.JButton btnSquare;
    private javax.swing.JButton btnSub;
    private javax.swing.JButton btnSum;
    private javax.swing.JButton btnTan;
    private javax.swing.JButton btnXY;
    private javax.swing.JButton btnXor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkHyp;
    private javax.swing.JCheckBox chkInv;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblResult;
    private javax.swing.JRadioButton rbtnBin;
    private javax.swing.JRadioButton rbtnDec;
    private javax.swing.JRadioButton rbtnDegrees;
    private javax.swing.JRadioButton rbtnGrads;
    private javax.swing.JRadioButton rbtnHex;
    private javax.swing.JRadioButton rbtnOct;
    private javax.swing.JRadioButton rbtnRadians;
    private javax.swing.JToggleButton tbtnAve;
    private javax.swing.JToggleButton tbtnDat;
    private javax.swing.JToggleButton tbtnS;
    private javax.swing.JToggleButton tbtnSta;
    private javax.swing.JToggleButton tbtnSum;
    // End of variables declaration//GEN-END:variables
}
