package part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class AddingPart {
	 
    private Text txtInput;
 
    @Inject
    private MDirtyable dirty;
    
    @Inject
    EPartService partService;
    
    //SamplePart samplePart = (SamplePart)(partService.findPart("rcpapplication.part.sample").getObject());
 
    @PostConstruct
    public void createComposite(Composite parent) {
        parent.setLayout(new GridLayout(1, false));
 
        txtInput = new Text(parent, SWT.BORDER);
        txtInput.setMessage("Enter text to mark part as dirty");
        txtInput.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                dirty.setDirty(true);
            }
        });
        
        txtInput.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				if(keyEvent.keyCode == 13) {
					SamplePart part = (SamplePart)(partService.findPart("rcpapplication.part.sample").getObject());
					part.getTable().add(txtInput.getText());
					txtInput.setText("");
				}
			}
        	
        });
        txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    }
 
    @Focus
    public void setFocus() {
        txtInput.setFocus();
    }
 
    @Persist
    public void save() {
        dirty.setDirty(false);
    }
}