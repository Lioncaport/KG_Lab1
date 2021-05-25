package workspace;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Main extends JDialog
{
    private JButton OKButton;
    private JPanel externalPanel;
    private JPanel window;
    private JTextField x0TextField;
    private JSlider hSlider;
    private JTextField hTextField;
    private JSlider vSlider;
    private JTextField vTextField;
    private JTextField y0TextField;
    private JTextField z0TextField;
    private JTextField x1TextField;
    private JTextField y1TextField;
    private JTextField z1TextField;
    private JTextField bettaTextField;

    public void count_of_horizont()
    {
        hTextField.setText(Integer.toString(hSlider.getValue()));
    }

    public void count_of_vertical()
    {
        vTextField.setText(Integer.toString(vSlider.getValue()));
    }

    public Main()
    {
        place myPlace = new place();
        window.add(myPlace);
        setContentPane(externalPanel);
        count_of_horizont();
        count_of_vertical();

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });

        hSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                count_of_horizont();
                myPlace.hslid = hSlider.getValue();
                myPlace.repaint();
            }
        });

        vSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                count_of_vertical();
                myPlace.vslid = vSlider.getValue();
                myPlace.repaint();
            }
        });

        OKButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String sx0 = x0TextField.getText();
                String sy0 = y0TextField.getText();
                String sz0 = z0TextField.getText();
                String sx1 = x1TextField.getText();
                String sy1 = y1TextField.getText();
                String sz1 = z1TextField.getText();
                String betta = bettaTextField.getText();

                if (sx0.isEmpty() || sy0.isEmpty() || sz0.isEmpty() || sx1.isEmpty() || sy1.isEmpty() || sz1.isEmpty() || betta.isEmpty())
                {
                    JOptionPane.showMessageDialog(externalPanel, "Enter all field", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    myPlace.axis[0][0] = Integer.parseInt(sx0);
                    myPlace.axis[0][1] = Integer.parseInt(sy0);
                    myPlace.axis[0][2] = Integer.parseInt(sz0);
                    myPlace.axis[1][0] = Integer.parseInt(sx1);
                    myPlace.axis[1][1] = Integer.parseInt(sy1);
                    myPlace.axis[1][2] = Integer.parseInt(sz1);
                    myPlace.betta = Integer.parseInt(betta);
                    myPlace.repaint();
                }
            }
        });
    }

    public static void main(String[] args)
    {
        Main dialog = new Main();
        dialog.setTitle("8307 -- Tkachev Igor, Guseinov Akshin -- Var 6");
        dialog.setSize(1300,700);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
}
