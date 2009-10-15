package cbit.vcell.microscopy.gui.estparamwizard;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cbit.vcell.microscopy.FRAPModel;

public class FitModelRadioButtonPanel extends JPanel
{
	private JRadioButton diffOneRadioButton = null;
	private JRadioButton diffTwoRadioButton = null;
	private JRadioButton diffBindingRadioButton = null;
	
	public FitModelRadioButtonPanel() {
		super();
//		setBackground(Color.white);
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0,7,7};
		setLayout(gridBagLayout);

		diffOneRadioButton = new JRadioButton();
//		diffOneRadioButton.setBackground(Color.white);
		diffOneRadioButton.setText("Diffusion with One Diffusing Component");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		add(diffOneRadioButton, gridBagConstraints);

		diffTwoRadioButton = new JRadioButton();
//		diffTwoRadioButton.setBackground(Color.white);
		diffTwoRadioButton.setText("Diffusion with Two Diffusing Components");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 0;
		add(diffTwoRadioButton, gridBagConstraints_1);

		diffBindingRadioButton = new JRadioButton();
//		diffBindingRadioButton.setBackground(Color.white);
		diffBindingRadioButton.setText("Diffusion plus Binding");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		add(diffBindingRadioButton, gridBagConstraints_2);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(diffOneRadioButton);
		bg.add(diffTwoRadioButton);
		bg.add(diffBindingRadioButton);
		
	}

	public void setBestModelRadioButton(int bestModel)
	{
		if(bestModel == FRAPModel.IDX_MODEL_DIFF_ONE_COMPONENT)
		{
			diffOneRadioButton.setSelected(true);
		}
		else if(bestModel == FRAPModel.IDX_MODEL_DIFF_TWO_COMPONENTS)
		{
			diffTwoRadioButton.setSelected(true);
		}
		else if(bestModel == FRAPModel.IDX_MODEL_DIFF_BINDING)
		{
			diffBindingRadioButton.setSelected(true);
		}
			
	}
	
	public int getBestModelIndex()
	{
		if(diffOneRadioButton.isSelected())
		{
			return FRAPModel.IDX_MODEL_DIFF_ONE_COMPONENT;
		}
		else if(diffTwoRadioButton.isSelected())
		{
			return FRAPModel.IDX_MODEL_DIFF_TWO_COMPONENTS;
		}
		else 
		{
			return FRAPModel.IDX_MODEL_DIFF_BINDING;
		}
	}
	
	public void disableAllRadioButtons()
	{
		diffOneRadioButton.setEnabled(false);
		diffTwoRadioButton.setEnabled(false);
		diffBindingRadioButton.setEnabled(false);
	}
	
	public void enableRadioButton(int modelIdx)
	{
		if(modelIdx == FRAPModel.IDX_MODEL_DIFF_ONE_COMPONENT)
		{
			diffOneRadioButton.setEnabled(true);
		}
		else if(modelIdx == FRAPModel.IDX_MODEL_DIFF_TWO_COMPONENTS)
		{
			diffTwoRadioButton.setEnabled(true);
		}
		else if(modelIdx == FRAPModel.IDX_MODEL_DIFF_BINDING)
		{
			diffBindingRadioButton.setEnabled(true);
		}
	}
}
