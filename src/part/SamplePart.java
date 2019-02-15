package part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class SamplePart {
	
	public static final String ID = "rcpapplication.part.sample";
	 
    private TableViewer tableViewer;
 
    @Inject
    private MDirtyable dirty;
 
    @PostConstruct
    public void createComposite(Composite parent) {
        parent.setLayout(new GridLayout(1, false));
 
        tableViewer = new TableViewer(parent);
 
        tableViewer.add("Sample item 1");
        tableViewer.add("Sample item 2");
        tableViewer.add("Sample item 3");
        tableViewer.add("Sample item 4");
        tableViewer.add("Sample item 5");
        tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
    }
    
    public TableViewer getTable() {
    	return tableViewer;
    }
 
    @Focus
    public void setFocus() {
        tableViewer.getTable().setFocus();
    }
 
    @Persist
    public void save() {
        dirty.setDirty(false);
    }
}