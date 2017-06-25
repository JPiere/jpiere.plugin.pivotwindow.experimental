/******************************************************************************
 * Product: JPiere                                                            *
 * Copyright (C) Hideaki Hagiwara (h.hagiwara@oss-erp.co.jp)                  *
 *                                                                            *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY.                          *
 * See the GNU General Public License for more details.                       *
 *                                                                            *
 * JPiere is maintained by OSS ERP Solutions Co., Ltd.                        *
 * (http://www.oss-erp.co.jp)                                                 *
 *****************************************************************************/
package jpiere.plugin.pivotwindow.form;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import org.adempiere.util.Callback;
import org.adempiere.webui.AdempiereWebUI;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Group;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WebEditorFactory;
import org.adempiere.webui.event.ActionEvent;
import org.adempiere.webui.event.ActionListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.info.IWhereClauseEditor;
import org.adempiere.webui.info.WInfoPAttributeEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.theme.ThemeManager;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.AccessSqlParser;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MRole;
import org.compiere.model.MTable;
import org.compiere.model.X_AD_InfoColumn;
import org.compiere.model.AccessSqlParser.TableInfo;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;
import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.PivotRendererExt;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.impl.TabularPivotField;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.ui.PivotFieldControl;
import org.zkoss.pivot.util.Exports;
import org.zkoss.pivot.util.Exports.PivotExportContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vlayout;

import jpiere.plugin.pivotwindow.model.MPivotWindow;
import jpiere.plugin.pivotwindow.model.MPivotWindowDefault;
import jpiere.plugin.pivotwindow.model.MPivotWindowDefaultField;
import jpiere.plugin.pivotwindow.model.MPivotWindowField;



/**
 * JPIERE-0356: Pivot Window
 * 
 * @author Hideaki Hagiwara
 *
 */
public class JPierePivotWindow extends AbstractPivotWindowForm implements EventListener<Event>, ValueChangeListener,WTableModelListener,ActionListener {
	
	/**	Logger			*/
	private  static CLogger log = CLogger.getCLogger(JPierePivotWindow.class);

    private CustomForm form = new CustomForm();
    
    private static final int FIELDLENGTH = 20;
    
	/**********************************************************************
	 * Model
	 **********************************************************************/   
    
    private MPivotWindow m_pivotWindow = null;
	private MPivotWindowField[] m_PivotWindowFields = null;
	private MPivotWindowDefault[] m_pivotWindowDefaults = null;
	private int pivotWindowDefaults_ID = 0;
    
    private ArrayList<String> columnNames = new ArrayList<String>();
	private List<GridField> gridFields;
	protected Map<String, WEditor> editorMap = new HashMap<String, WEditor>();
	protected PivotWindowFieldInfo[] pivotWindowFieldsInfo;
    
    
	/**********************************************************************
	 * UI Component
	 **********************************************************************/   
    
    //Main Component
    private Pivottable pivot;
    private PivotRendererExt renderer = null;
    private TabularPivotModel pivotModel ;

    //Layout Panel
    private Grid baseGrid ;
    private Grid searchFieldGrid;
    private Grid predefinedButtonGrid;
    private Grid pivotFieldControllerGrid;
    private Grid otherPivotControllerGrid;
    private Grid protrudePivotControllerGrid;
	private Vlayout pivotVlayout = new Vlayout();
	
    //Pivot Parameters Component
    private PivotFieldControl pivotFieldControl = null;
    private Checkbox colGrandTotal = new Checkbox(Msg.getMsg(Env.getCtx(), "JP_ColGrandTotal"));//Enable grand total for columns
    private Checkbox rowGrandTotal = new Checkbox(Msg.getMsg(Env.getCtx(), "JP_RowGrandTotal"));//Enable grand total for rows
    private Checkbox autoWrap = new Checkbox(Msg.getMsg(Env.getCtx(), "JP_AutoWrap"));//Enable Auto Wrap
    
    private Radio colOrient = new Radio(Msg.getMsg(Env.getCtx(), "JP_Column"));//Column
    private Radio rowOrient = new Radio(Msg.getMsg(Env.getCtx(), "JP_Row"));//Row
    
	//List of Search Field Editors
	private List<WEditor> editors;
	private Checkbox checkAND;
	
	//Button and other
	private Button searchButton;
	private Button downLoadButton;
	private Button downLoadXLS ;
	private Button downLoadCSV ;
	private Button downLoadXLSX ;
	
	private WNumberEditor toAddUpRecordsNum = new WNumberEditor("JP_ToAddUpRecordsNum", true, false, true, DisplayType.Integer, "");
	private WNumberEditor pageSize = new WNumberEditor("JP_PageSize", true, false, true, DisplayType.Integer, "");
	
	
	
	/**********************************************************************
	 * Others
	 **********************************************************************/   
	
	private TableInfo[] 	tableInfos;
	private int				p_WindowNo;
	/** Table Name              */
	private String            p_tableName;
	/** Initial WHERE Clause    */
	private String			p_whereClause = "";

	private boolean m_lookup;
	
	protected List<Object> prevParameterValues = null;
	protected List<String> prevQueryOperators = null;
	protected List<WEditor> prevRefParmeterEditor = null;
	
	
	
	public JPierePivotWindow() 
	{
		;//Nothing to do;
	}
	
	
	@Override
	public void createPivotWindow(String JP_PivotWindow_ID) 
	{
		try
		{
			/**Prepare*/
			p_WindowNo = form.getWindowNo();
			m_pivotWindow = MPivotWindow.get((new Integer(JP_PivotWindow_ID)).intValue(), null);
			if(preparePivot())
			{
				;//TODO Error
			}
			
			pivot = new Pivottable();
			pivot.addEventListener("onPivotContext", this);
			pivotModel = new TabularPivotModel(new ArrayList<List<Object>>(), columnNames);
			pivot.setModel(pivotModel);
			pivot.appendChild(new Label(m_pivotWindow.get_Translation("Name")));
			pivot.appendChild(new Label(Msg.getMsg(Env.getCtx(), "JP_Column")));
			pivot.appendChild(new Label(Msg.getMsg(Env.getCtx(), "JP_Row")));
	      
			pivotFieldControl = new PivotFieldControl();
			
			pivotFieldControl.setAttribute("rowListTitle", Msg.getMsg(Env.getCtx(), "JP_Row"));
			pivotFieldControl.setAttribute("columnListTitle", Msg.getMsg(Env.getCtx(), "JP_Column"));
			pivotFieldControl.setAttribute("dataListTitle", Msg.getMsg(Env.getCtx(), "JP_PivotValueFields"));
			pivotFieldControl.setAttribute("unusedListTitle", Msg.getMsg(Env.getCtx(), "JP_PivotUnusedFields"));
			pivotFieldControl.setAttribute("sortAsc", Msg.getMsg(Env.getCtx(), "JP_Asc"));
			pivotFieldControl.setAttribute("sortDesc", Msg.getMsg(Env.getCtx(), "JP_Desc"));
			
			pivotFieldControl.afterCompose();
			pivotFieldControl.setModel(pivotModel);
			pivotFieldControl.setLayout("horizontal");
			if(m_pivotWindowDefaults != null && m_pivotWindowDefaults.length > 0)
				loadConfiguration(m_pivotWindowDefaults[0]);
			else
				loadConfigurationDefault();
			
    		/**Rendering(zkInit)*/
    		baseGrid = GridFactory.newGridLayout();
    		baseGrid.newRows();
    		
    		form.setSclass("pivotwindow");
    		ZKUpdateUtil.setWidth(form, "100%");
    		ZKUpdateUtil.setHeight(form, "100%");
    		form.appendChild(baseGrid);
    	
    		renderPivtoWindowController();
    		
    		form.appendChild(new Separator());
    		
    		pivotVlayout.appendChild(pivot);
    		pivot.setVisible(false);
    		form.appendChild(pivotVlayout);
    		
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}
	
	
	protected boolean preparePivot() 
	{
		String tableName = null;
		tableName = MTable.getTableName(Env.getCtx(), m_pivotWindow.getAD_Table_ID());
	
		AccessSqlParser sqlParser = new AccessSqlParser("SELECT * FROM " + m_pivotWindow.getFromClause());
		tableInfos = sqlParser.getTableInfo(0);
		if (tableInfos[0].getSynonym() != null && tableInfos[0].getSynonym().trim().length() > 0) 
		{
			p_tableName = tableInfos[0].getSynonym().trim();
			if (p_whereClause != null && p_whereClause.trim().length() > 0) 
			{
				p_whereClause = p_whereClause.replace(tableName+".", p_tableName+".");
			}					
		}
		
		m_PivotWindowFields = m_pivotWindow.getPivotWindowFields();
		gridFields = new ArrayList<GridField>();
		for(MPivotWindowField pivotField : m_PivotWindowFields) 
		{
			String columnName = pivotField.getColumnName();
			/*!m_lookup && infoColumn.isMandatory():apply Mandatory only case open as window and only for criteria field*/
			boolean isMandatory = !m_lookup && pivotField.isMandatory() && pivotField.isQueryCriteria();
			GridFieldVO vo = GridFieldVO.createParameter(Env.getCtx(), p_WindowNo, AEnv.getADWindowID(p_WindowNo), 0, 0,
					columnName, pivotField.get_Translation("Name"), pivotField.getAD_Reference_ID(), 
					pivotField.getAD_Reference_Value_ID(), isMandatory, false);
			if (pivotField.getAD_Val_Rule_ID() > 0) 
			{
				vo.ValidationCode = pivotField.getAD_Val_Rule().getCode();
				if (vo.lookupInfo != null) 
				{
					vo.lookupInfo.ValidationCode = vo.ValidationCode;
					vo.lookupInfo.IsValidated = false;
				}
			}
			if(pivotField.getDisplayLogic() != null)					
				vo.DisplayLogic =  pivotField.getDisplayLogic();
			if(pivotField.isQueryCriteria() && pivotField.getDefaultValue() != null)
				vo.DefaultValue = pivotField.getDefaultValue();
			if(pivotField.getMandatoryLogic() != null && !pivotField.isMandatory())
				vo.MandatoryLogic = pivotField.getMandatoryLogic();
			String desc = pivotField.get_Translation("Description");
			vo.Description = desc != null ? desc : "";
			String help = pivotField.get_Translation("Help");
			vo.Help = help != null ? help : "";
			GridField gridField = new GridField(vo);
			gridFields.add(gridField);
			
			if(pivotField.isDisplayed())
				columnNames.add(pivotField.get_Translation("Name"));
		}
		
		
		StringBuilder builder = new StringBuilder(p_whereClause != null ? p_whereClause.trim() : "");
		String pivotWhereClause = m_pivotWindow.getWhereClause();
		if (pivotWhereClause != null && pivotWhereClause.indexOf("@") >= 0) 
		{
			pivotWhereClause = Env.parseContext(Env.getCtx(), p_WindowNo, pivotWhereClause, true, false);
			if (pivotWhereClause.length() == 0)
				log.log(Level.SEVERE, "Cannot parse context= " + m_pivotWindow.getWhereClause());
		}
		
		if (pivotWhereClause != null && pivotWhereClause.trim().length() > 0) 
		{								
			if (builder.length() > 0) 
			{
				builder.append(" AND ");
			}
			builder.append(pivotWhereClause);
			p_whereClause = builder.toString();
		}
		
		m_pivotWindowDefaults = m_pivotWindow.getPivotWindowDefaults();
		
		return true;
	}
	
	
	protected void renderPivtoWindowController() 
	{
		Group pivotParameterGroup = new Group(Msg.getMsg(Env.getCtx(), "JP_PivotWindowController"));//Pivot Window Controller
		Cell cell = (Cell) pivotParameterGroup.getFirstChild();
		cell.setSclass("z-group-inner");
		baseGrid.getRows().appendChild(pivotParameterGroup);
		
		/**Search Fields*/
		createSearchFieldGrid();
		if(editors.size() > 0)
		{
			Row searchFieldRow = new Row();
			searchFieldRow.setGroup(pivotParameterGroup);
			searchFieldRow.appendChild(searchFieldGrid);
			baseGrid.getRows().appendChild(searchFieldRow);
		}
		
		
		/**Predefind Buttons*/
		createPredefinedButtonGrid();
		if(predefinedButtonGrid != null)
		{
			Row predefinedButtonRow = new Row();
			predefinedButtonRow.setGroup(pivotParameterGroup);
			predefinedButtonRow.appendChild(predefinedButtonGrid);
			baseGrid.getRows().appendChild(predefinedButtonRow);
		}
		
		
		/**PivotFieldControl*/
		createPivotFieldControllerGrid();
		Row pivotFieldControllerRow = new Row();
		pivotFieldControllerRow.setGroup(pivotParameterGroup);
		pivotFieldControllerRow.appendChild(pivotFieldControllerGrid);
		baseGrid.getRows().appendChild(pivotFieldControllerRow);
		
		
		/**Other Pivot Controller*/
		createOtherPivotControllerGrid();
		Row otherPivotControllerRow = new Row();
		otherPivotControllerRow.setGroup(pivotParameterGroup);
		otherPivotControllerRow.appendChild(otherPivotControllerGrid);
		baseGrid.getRows().appendChild(otherPivotControllerRow);
		
		
		/**Protrude Pivot Controller*/
		createProtrudePivotControllerGrid();
		Row protrudePivotControllerRow = new Row();
		protrudePivotControllerRow.appendChild(protrudePivotControllerGrid);
		baseGrid.getRows().appendChild(protrudePivotControllerRow);		
		
	}
	
	
	protected void createSearchFieldGrid() 
	{
		searchFieldGrid = GridFactory.newGridLayout();
		searchFieldGrid.setClass("pivotwindow-searchFieldGrid");
		
		Columns columns = new Columns();
		searchFieldGrid.appendChild(columns);
		for(int i = 0; i < 6; i++)
			columns.appendChild(new Column());
		
		Column column = new Column();
		ZKUpdateUtil.setWidth(column, "100px");
		column.setAlign("right");
		columns.appendChild(column);
		
		Rows rows = new Rows();		
		searchFieldGrid.appendChild(rows);
		editors = new ArrayList<WEditor>();
		TreeMap<Integer, List<Object[]>> tree = new TreeMap<Integer, List<Object[]>>();
		for (int i = 0; i < m_PivotWindowFields.length; i++)
		{
			if (m_PivotWindowFields[i].isQueryCriteria()) {
				List<Object[]> list = tree.get(m_PivotWindowFields[i].getSeqNoSelection());
				if (list == null) {
					list = new ArrayList<Object[]>();
					tree.put(m_PivotWindowFields[i].getSeqNoSelection(), list);
				}
				list.add(new Object[]{m_PivotWindowFields[i], gridFields.get(i)});		
				
			}
		}
		
		for (Integer i : tree.keySet()) {
			List<Object[]> list = tree.get(i);
			for(Object[] value : list) {
				addSelectionColumn((MPivotWindowField)value[0], (GridField)value[1]);
			}
		}
		
		if (checkAND == null) {
			if (searchFieldGrid.getRows() != null && searchFieldGrid.getRows().getFirstChild() != null) {
				Row row = (Row) searchFieldGrid.getRows().getFirstChild();
				int col = row.getChildren().size();
				while (col < 6) {
					row.appendChild(new Space());
					col++;
				}
				createAndCheckbox();
				row.appendChild(checkAND);
			}
		}

		dynamicDisplay();
	}
	
	
	protected void createPredefinedButtonGrid()
	{
		m_pivotWindowDefaults = m_pivotWindow.getPivotWindowDefaults();
		
		if(m_pivotWindowDefaults == null || m_pivotWindowDefaults.length==0)
			return ;
		
		predefinedButtonGrid = GridFactory.newGridLayout();		
		predefinedButtonGrid.setClass("pivotwindow-predefinedButtonGrid");
		Columns columns = new Columns();
		predefinedButtonGrid.appendChild(columns);
		for(int i = 0; i < 4; i++)
			columns.appendChild(new Column());
		
		Rows rows = new Rows();	
		predefinedButtonGrid.appendChild(rows);
		Row row = new Row();
		rows.appendChild(row);
		for (int i = 0; i < m_pivotWindowDefaults.length; i++)
		{
			String name = m_pivotWindowDefaults[i].get_Translation("Name");
			Button scenarioBtn = new Button(name);
			scenarioBtn.setSclass("pivot-predefine");
			scenarioBtn.setHflex("true");
			scenarioBtn.setId(String.valueOf(i));
			scenarioBtn.addEventListener("onClick", new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					loadConfiguration(event);
				}
			});
			
			//Auto Wrap for Predefine Buttons
			if(i != 0 && i%4 == 0)
			{
				row = new Row();
				rows.appendChild(row);
			}
			row.appendChild(scenarioBtn);
		}
		
	}
	
	
	protected void createPivotFieldControllerGrid()
	{
		pivotFieldControllerGrid = GridFactory.newGridLayout();	
		pivotFieldControllerGrid.setClass("pivotwindow-pivotFieldControllerGrid");
		Rows rows = new Rows();	
		pivotFieldControllerGrid.appendChild(rows);
		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(pivotFieldControl);
		
	}
	
	
	protected void createOtherPivotControllerGrid()
	{
		otherPivotControllerGrid = GridFactory.newGridLayout();	
		otherPivotControllerGrid.setClass("pivotwindow-otherPivotControllerGrid");
		Rows rows = new Rows();	
		otherPivotControllerGrid.appendChild(rows);
		Row row = new Row();
		rows.appendChild(row);
		
		Radiogroup radioGroup = new  Radiogroup();
		row.appendChild(radioGroup);
		radioGroup.appendChild(new Label(Msg.getMsg(Env.getCtx(), "JP_DataFieldOrientation")) );//Data field orientation
		radioGroup.appendChild(colOrient);
		radioGroup.appendChild(rowOrient);
		radioGroup.addEventListener("onCheck", this);
		
		row.appendChild(colGrandTotal);
		colGrandTotal.addEventListener("onCheck", this);

		row.appendChild(rowGrandTotal);
		rowGrandTotal.addEventListener("onCheck", this);
		
		row.appendChild(autoWrap);
		autoWrap.addEventListener("onCheck", this);
	}
	
	
	protected void createProtrudePivotControllerGrid()
	{
		protrudePivotControllerGrid = GridFactory.newGridLayout();			
		protrudePivotControllerGrid.setClass("pivotwindow-protrudePivotControllerGrid");
		Columns columns = new Columns();
		protrudePivotControllerGrid.appendChild(columns);
		for(int i = 0; i < 8; i++)
			columns.appendChild(new Column());
		
		Rows protrudeRows = new Rows();	
		protrudePivotControllerGrid.appendChild(protrudeRows);
		
		Row protrudeRow = new Row();
		protrudeRows.appendChild(protrudeRow);
		
		searchButton = new Button();
		searchButton.setId("renderButton");
		searchButton.setImage(ThemeManager.getThemeResource("images/Report16.png"));
		searchButton.setLabel(Msg.getMsg(Env.getCtx(), "view.report"));
		searchButton.addActionListener(this);
		searchButton.setHflex("true");
		protrudeRow.appendChild(searchButton);
		
		downLoadButton = new Button();
		downLoadButton.setId("downLoadButton");
		downLoadButton.addActionListener(this);
		downLoadButton.setImage(ThemeManager.getThemeResource("images/Export16.png"));
		downLoadButton.setLabel(Msg.getMsg(Env.getCtx(), "Export"));
		downLoadButton.setEnabled(false);
		downLoadButton.setHflex("true");
		protrudeRow.appendChild(downLoadButton);
		
		Label recordNumLabel = new Label(Msg.getMsg(Env.getCtx(), "JP_ToAddUpRecordsNum"));
		protrudeRow.appendChild(recordNumLabel.rightAlign());
		toAddUpRecordsNum.setValue(0);
		toAddUpRecordsNum.setReadWrite(false);
		toAddUpRecordsNum.addValueChangeListener(this);
		toAddUpRecordsNum.getComponent().setHflex("true");
		protrudeRow.appendChild(toAddUpRecordsNum.getComponent());
		
		Label pageSizeLabel = new Label(Msg.getElement(Env.getCtx(), "JP_PageSize"));
		protrudeRow.appendChild(pageSizeLabel.rightAlign());
		pageSize.addValueChangeListener(this);
		pageSize.getComponent().setHflex("true");
		protrudeRow.appendChild(pageSize.getComponent());
		
	}
	
	
	protected void dynamicDisplay() 
	{
		for (int i = 0; i < editors.size(); i++)
		{
			WEditor editor = editors.get(i);
			GridField mField = editor.getGridField();
			if (mField.isDisplayed(true)) 
			{
				if (!editor.isVisible()) 
					editor.setVisible(true);
				
				boolean rw = mField.isEditablePara(true); // r/w - check if
															// field is Editable
				editor.setReadWrite(rw);
				editor.dynamicDisplay();

			} else if (editor.isVisible()) {
				editor.setVisible(false);
			}
			editor.setMandatory(mField.isMandatory(true));
        	editor.updateStyle();
		}
		
		if (getParent() != null) 
			getParent().invalidate();

	}
	
	
	protected boolean validateField (WEditor wEditor)
	{
		if (wEditor == null || !wEditor.isVisible() || wEditor.getGridField() == null){
			return true;
		}
		
		GridField validateGrid = wEditor.getGridField();
		// eval only mandatory field
		if (validateGrid.isMandatory(true))
		{
			// update color of field
			wEditor.updateLabelStyle();
			Object data = wEditor.getValue();
			if (data == null || data.toString().length() == 0) 
			{				
				return false;
			}
		}
		
		return true;		
	}
	
	
   protected void addSelectionColumn(MPivotWindowField pivotWindowField, GridField mField)
   {
        int displayLength = mField.getDisplayLength();
        if (displayLength <= 0 || displayLength > FIELDLENGTH)
            mField.setDisplayLength(FIELDLENGTH);
        else
            displayLength = 0;

        //  Editor
        WEditor editor = null;
        if (mField.getDisplayType() == DisplayType.PAttribute) 
        {
        	editor = new WInfoPAttributeEditor(Env.getCtx(), p_WindowNo, mField);
	        editor.setReadWrite(true);
        }
        else 
        {
	        editor = WebEditorFactory.getEditor(mField, false);
	        editor.setReadWrite(true);
	        editor.dynamicDisplay();
	        editor.addValueChangeListener(this);
	        editor.fillHorizontal();
        }
        Label label = editor.getLabel();
        Component fieldEditor = editor.getComponent();

        //
        if (displayLength > 0)      //  set it back
            mField.setDisplayLength(displayLength);
        //
        if (label != null) {
        	if (pivotWindowField.getQueryOperator().equals(X_AD_InfoColumn.QUERYOPERATOR_Gt) ||
        		pivotWindowField.getQueryOperator().equals(X_AD_InfoColumn.QUERYOPERATOR_GtEq) ||
        		pivotWindowField.getQueryOperator().equals(X_AD_InfoColumn.QUERYOPERATOR_Le) ||
        		pivotWindowField.getQueryOperator().equals(X_AD_InfoColumn.QUERYOPERATOR_LeEq) ||
        		pivotWindowField.getQueryOperator().equals(X_AD_InfoColumn.QUERYOPERATOR_NotEq )) {
        		label.setValue(label.getValue() + " " + pivotWindowField.getQueryOperator());
        	}
        }

        addSearchParameter(label, fieldEditor);
        
        editor.setProcessParameter(true);
        editors.add(editor);
        
        editor.showMenu();

        fieldEditor.addEventListener(Events.ON_OK, this);		

        mField.addPropertyChangeListener(editor);
                
        mField.setValue(mField.getDefaultForPanel(), true);

    }   // addSelectionColumn

   
	protected void addSearchParameter(Label label, Component fieldEditor) 
	{
		Row panel = null;
        if (searchFieldGrid.getRows().getChildren().isEmpty())
        {
        	panel = new Row();
        	searchFieldGrid.getRows().appendChild(panel);
        }
        else
        {
        	panel = (Row) searchFieldGrid.getRows().getLastChild();
        	if (panel.getChildren().size() == 6)
        	{
        		if (searchFieldGrid.getRows().getChildren().size() == 1) 
        		{
        			createAndCheckbox();
					panel.appendChild(checkAND);
        		}
        		else
        		{
        			panel.appendChild(new Space());
        		}
        		panel = new Row();
            	searchFieldGrid.getRows().appendChild(panel); 
        	}
        }
        if (!(fieldEditor instanceof Checkbox))
        {
        	Div div = new Div();
        	div.setStyle("text-align: right;");
        	div.appendChild(label);
        	if (label.getDecorator() != null){
        		div.appendChild (label.getDecorator());
        	}
        	panel.appendChild(div);
        } else {
        	panel.appendChild(new Space());
        }
        
        // add out parent to add menu for this field, without outerDiv, a new cell will auto make for menu.
        Div outerParent = new Div();
        outerParent.appendChild(fieldEditor);
        panel.appendChild(outerParent);
	}
	

	protected void createAndCheckbox() 
	{
		checkAND = new Checkbox();
		checkAND.setLabel(Msg.getMsg(Env.getCtx(), "SearchAND", true));
		String tips = Msg.getMsg(Env.getCtx(), "SearchAND", false);
		if (!Util.isEmpty(tips)) 
		{
			checkAND.setTooltiptext(tips);
		}
		checkAND.setChecked(true);
		checkAND.setVisible(false);
		checkAND.addEventListener(Events.ON_CHECK, this);
	}
	
	
	@Override
	public ADForm getForm() {
		return form;
	}
	
	
	private void loadConfigurationDefault() 
	{
		pivotModel.clearAllFields(true);	
		
		pivot.setGrandTotalForColumns(false);
		colGrandTotal.setChecked(false);
		
		pivot.setGrandTotalForRows(false);
		rowGrandTotal.setChecked(false);
		
		colOrient.setChecked(false);
		rowOrient.setChecked(true);
		pivot.setDataFieldOrient("row");
		
		pageSize.setValue(20);
		pivot.setPageSize(new Integer(pageSize.getValue().toString()).intValue());
		
		pivotFieldControl.syncModel(); // field control	
	}
	
	
	private void loadConfiguration(Event event) 
	{
		Button btn =(Button)event.getTarget();
		pivotWindowDefaults_ID = Integer.parseInt(btn.getId());
		
		
		if(m_pivotWindowDefaults == null)
			return;
		
		MPivotWindowDefault pivotWindowDefault = m_pivotWindowDefaults[pivotWindowDefaults_ID];
		loadConfiguration(pivotWindowDefault);		
	}
	
	
	private void loadConfiguration(MPivotWindowDefault pivotWindowDefault) 
	{
		pivotModel.clearAllFields(true);
		MPivotWindowDefaultField[] defaultFields = pivotWindowDefault.getPivotWindowDefaultFields();
		
		for(int i = 0; i < defaultFields.length; i++)
		{
			MPivotWindowField pivotWindowField = MPivotWindowField.get(defaultFields[i].getJP_PivotWindowField_ID());
			String fieldName = pivotWindowField.get_Translation("Name");
			
			if(defaultFields[i].getJP_PivotFieldPosition().equals(MPivotWindowDefaultField.JP_PIVOTFIELDPOSITION_Columns))
				pivotModel.setFieldType(fieldName, PivotField.Type.COLUMN);
			else if(defaultFields[i].getJP_PivotFieldPosition().equals(MPivotWindowDefaultField.JP_PIVOTFIELDPOSITION_Rows))
				pivotModel.setFieldType(fieldName, PivotField.Type.ROW);
			else if(defaultFields[i].getJP_PivotFieldPosition().equals(MPivotWindowDefaultField.JP_PIVOTFIELDPOSITION_Valuse))
				pivotModel.setFieldType(fieldName, PivotField.Type.DATA);
			else if(defaultFields[i].getJP_PivotFieldPosition().equals(MPivotWindowDefaultField.JP_PIVOTFIELDPOSITION_Unused))
				pivotModel.setFieldType(fieldName, PivotField.Type.UNUSED);
		}
		
		renderer = new JPierePivotRenderer(m_pivotWindow.getFormatPattern());
		
		pivot.setPivotRenderer(renderer);
		
		String dataFieldOrientation = pivotWindowDefault.getJP_DataFieldOrientation();
		if(dataFieldOrientation.equals("C"))
		{
			rowOrient.setChecked(false);
			colOrient.setChecked(true);
			pivot.setDataFieldOrient("column");
		}else{
			colOrient.setChecked(false);
			rowOrient.setChecked(true);
			pivot.setDataFieldOrient("row");
		}
		

		pivot.setGrandTotalForColumns(pivotWindowDefault.isDisplayTotalColumnJP());
		colGrandTotal.setChecked(pivotWindowDefault.isDisplayTotalColumnJP());
		
		pivot.setGrandTotalForRows(pivotWindowDefault.isDisplayTotalRowJP());
		rowGrandTotal.setChecked(pivotWindowDefault.isDisplayTotalRowJP());
		
		autoWrap.setChecked(pivotWindowDefault.isAutoWrapJP());
		pivot.setAutowrap(pivotWindowDefault.isAutoWrapJP());

		pageSize.setValue(pivotWindowDefault.getJP_PageSize());
		pivot.setPageSize(pivotWindowDefault.getJP_PageSize());
		
		pivotFieldControl.syncModel(); // field control
	}


	@Override
	public void actionPerformed(ActionEvent event) 
	{
		;
	}

	
	@Override
	public void tableChanged(WTableModelEvent event) 
	{
		;
	}

	
	@Override
	public void valueChange(ValueChangeEvent e) 
	{
		if(e.getPropertyName().equals("JP_PageSize"))
		{
			Integer integer = (Integer)e.getNewValue();
			if(integer==null)
			{
				pageSize.setValue(0);
			}else{
				pageSize.setValue(e.getNewValue());
			}

			pivot.setPageSize(new Integer(pageSize.getValue().toString()).intValue());
			return ;
		
		
		}else if (e != null && e.getSource() instanceof WEditor){
			
			WEditor editor = (WEditor)e.getSource();            
            if (e.getNewValue() == null) 
            {
            	Env.setContext(Env.getCtx(), p_WindowNo, editor.getColumnName(), "");
            	Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, editor.getColumnName(), "");
            } else if (e.getNewValue() instanceof Boolean) {
            	Env.setContext(Env.getCtx(), p_WindowNo, editor.getColumnName(), (Boolean)e.getNewValue());
            	Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, editor.getColumnName(), (Boolean)e.getNewValue());
            } else if (e.getNewValue() instanceof Timestamp) {
            	Env.setContext(Env.getCtx(), p_WindowNo, editor.getColumnName(), (Timestamp)e.getNewValue());
            	Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO+"|"+editor.getColumnName(), (Timestamp)e.getNewValue());
            } else {
            	Env.setContext(Env.getCtx(), p_WindowNo, editor.getColumnName(), e.getNewValue().toString());
            	Env.setContext(Env.getCtx(), p_WindowNo, Env.TAB_INFO, editor.getColumnName(), e.getNewValue().toString());
            }
            
            //Sync editor value
    		for(WEditor ed : editors)
    		{
    			if(ed.getColumnName().equals(e.getPropertyName()))
    			{
    				editor = ed;
    				editor.setValue(e.getNewValue());
    				break;
    			}
    		}
    		
            
            dynamicDisplay();
            searchButton.setEnabled(true);
            downLoadButton.setEnabled(false);
            detachPivot();            	
        }
	}//valueChange
	
	
	@Override
	public void onEvent(Event event) throws Exception 
	{
		Component comp = event.getTarget();
		
		if(comp.equals(colGrandTotal))
		{
			Checkbox cb =(Checkbox)comp;
			colGrandTotal.setChecked(cb.isChecked());
			pivot.setGrandTotalForColumns(cb.isChecked());
			
		}else if(comp.equals(rowGrandTotal)){
			Checkbox cb =(Checkbox)comp;
			rowGrandTotal.setChecked(cb.isChecked());
			pivot.setGrandTotalForRows(cb.isChecked());
			
		}else if(comp.equals(autoWrap)){
			Checkbox cb =(Checkbox)comp;
			autoWrap.setChecked(cb.isChecked());
			pivot.setAutowrap(cb.isChecked());
			
		}else if(comp.equals(colOrient)){
			Radio radio = (Radio)comp;
			colOrient.setSelected(radio.isSelected());
			rowOrient.setSelected(!radio.isSelected());
			
			if(radio.isSelected())
				pivot.setDataFieldOrient("column");
			else
				pivot.setDataFieldOrient("row");
			
		}else if(comp.equals(rowOrient)){
			Radio radio = (Radio)comp;
			colOrient.setSelected(!radio.isSelected());
			rowOrient.setSelected(radio.isSelected());
			
			if(radio.isSelected())
				pivot.setDataFieldOrient("row");
			else
				pivot.setDataFieldOrient("column");
						
		}else if (comp.equals(searchButton)){
			
			if(validateParameters())
			{
				renderPivot();
				
			}else{
				FDialog.info(form.getWindowNo(), null, "FillMandatory");
			}

		}else if (comp.equals(downLoadButton)){
			
			PivotWindowButtonPopup popup = new PivotWindowButtonPopup();
			popup.setWidgetAttribute(AdempiereWebUI.WIDGET_INSTANCE_NAME, "processButtonPopup");

			List<org.zkoss.zul.Button> buttonList = new ArrayList<org.zkoss.zul.Button>();
			downLoadXLS = new Button();
			downLoadXLS.setId("downLoadXLS");
			downLoadXLS.addActionListener(this);
			downLoadXLS.setLabel("xls");
			downLoadXLS.setEnabled(true);
			buttonList.add(downLoadXLS);
			
			downLoadCSV = new Button();
			downLoadCSV.setId("downLoadCSV");
			downLoadCSV.addActionListener(this);
			downLoadCSV.setLabel("csv");
			downLoadCSV.setEnabled(true);
			buttonList.add(downLoadCSV);

//			downLoadXLSX = new Button();
//			downLoadXLSX.setId("downLoadXLSX");
//			downLoadXLSX.addActionListener(this);
//			downLoadXLSX.setLabel("xlsx");
//			downLoadXLSX.setEnabled(true);
//			buttonList.add(downLoadXLSX);
			
			popup.render(buttonList);
			LayoutUtils.openPopupWindow(downLoadButton, popup, "after_start");
			
		}else if (comp.equals(downLoadXLS)){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PivotExportContext context = Exports.getExportContext(pivot, false, null);
			Exports.exportExcel(out, "xls", context, null);
			m_pivotWindow.get_Translation("Name");
			Filedownload.save(out.toByteArray(), "application/vnd.ms-excel", m_pivotWindow.get_Translation("Name")+".xls");
			
			try { out.close(); } catch (IOException e) {}
			
		}else if (comp.equals(downLoadCSV)){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PivotExportContext context = Exports.getExportContext(pivot, false, null);
			Exports.exportCSV(out, context);
			Filedownload.save(out.toByteArray(), "text/csv", m_pivotWindow.get_Translation("Name")+".csv");
			
			try { out.close(); } catch (IOException e) {}
			
		}else if (comp.equals(downLoadXLSX)){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PivotExportContext context = Exports.getExportContext(pivot, false, null);
			Exports.exportExcel(out, "xlsx", context, null); 
			Filedownload.save(out.toByteArray(), "application/vnd.ms-excel", m_pivotWindow.get_Translation("Name")+".xlsx"); 
			
			try { out.close(); } catch (IOException e) {}
			
		}
			
	}

	
	public boolean validateParameters() 
	{
		boolean isValid = true;
		
		for (int i = 0; i < editors.size(); i++)
		{
			WEditor wEditor = (WEditor) editors.get(i);
			// cancel editor not display
			if (wEditor == null || !wEditor.isVisible() || wEditor.getGridField() == null)
			{
				continue;
			}
			
			isValid = isValid & validateField (wEditor);
		}
		
		return isValid;
	}
	
	private List<List<Object>> pivotDataList = new ArrayList<List<Object>>();	
	
	private void renderPivot()
	{
		pivotDataList.clear();
		
    	prepareTable();
    	testCount();
    	
		if (m_pivotWindow.getMaxQueryRecords() > 0 && m_count > m_pivotWindow.getMaxQueryRecords())
		{
			FDialog.info(p_WindowNo, this, "InfoFindOverMax", m_count + " > " + m_pivotWindow.getMaxQueryRecords());
			m_count = 0;
			return ;
		}
    	
		if(m_count == 0)
		{
			FDialog.info(p_WindowNo, this, Msg.getMsg(Env.getCtx(), "not.found"));
			return ;
		}
		
		if (m_pivotWindow.getConfirmQueryRecords() > 0 && m_count > m_pivotWindow.getConfirmQueryRecords())
		{
			FDialog.ask(form.getWindowNo(), null, "JP_ConfirmQueryRecords", Msg.getMsg(Env.getCtx(), "JP_ToAddUpRecordsNum") + " : "+ m_count, new Callback<Boolean>()
			{

				@Override
				public void onCallback(Boolean result)
				{
					if(result)
					{
						pivotDataList = executeGetDataQuery() ;
					}else{
						pivotDataList = null;
					}
		        }

			});//FDialog.
			
		}else{
			pivotDataList = executeGetDataQuery() ;
		}

	}
	
	
	private List<List<Object>> executeGetDataQuery()
	{
    	
		toAddUpRecordsNum.setValue(m_count);
    	
		final String sql = buildDataSQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			setParameters (pstmt, false);	//	no count
			rs = pstmt.executeQuery();
			while (rs.next())
			{			
				pivotDataList.add(readData(rs, pivotWindowFieldsInfo));
			}
				
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}		
		
		
		/**Get Now Pivot Window Parameter*/
		TabularPivotField[] fields = pivotModel.getFields();
		String dataFieldOrient = pivot.getDataFieldOrient();
		boolean isGrandTotalForColumns = colGrandTotal.isChecked();
		boolean isGrandTotalForRows = rowGrandTotal.isChecked();
		boolean isAutoWrap =  autoWrap.isChecked();
		
		pivot.setVisible(true);
		pivotModel = new TabularPivotModel(pivotDataList, columnNames);

		/**Reset to Now Pivot Window Parameter*/
		for(int i = 0; i < fields.length; i++)
		{
			pivotModel.setFieldType(fields[i].getFieldName(), fields[i].getType());
		}
		
		pivot.setDataFieldOrient(dataFieldOrient);
		pivot.setPageSize(Integer.parseInt(pageSize.getValue().toString()));
		pivot.setGrandTotalForColumns(isGrandTotalForColumns);
		pivot.setGrandTotalForRows(isGrandTotalForRows);
		pivot.setAutowrap(isAutoWrap);
		
		pivot.setModel(pivotModel);
		pivotFieldControl.setModel(pivotModel);
		
		searchButton.setEnabled(false);
		downLoadButton.setEnabled(true);
		
		return pivotDataList;
	}
	
	
	protected int m_count;
	
	/**
	 * 	Test Row Count
	 *	@return true if display
	 */
	protected boolean testCount()
	{
		long start = System.currentTimeMillis();
		String dynWhere = getSQLWhere();
		StringBuilder sql = new StringBuilder (m_sqlMain);

		if (dynWhere.length() > 0)
			sql.append(dynWhere);   //  includes first AND
		
		String countSql = Msg.parseTranslation(Env.getCtx(), sql.toString());	//	Variables
		if (countSql.trim().endsWith("WHERE")) {
			countSql = countSql.trim();
			countSql = countSql.substring(0, countSql.length() - 5);
		}
		
		countSql = MRole.getDefault().addAccessSQL(countSql, p_tableName, MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO);
		
		countSql = "SELECT COUNT(*) FROM ( " + countSql + " ) a";			
		
		if (log.isLoggable(Level.FINER))
			log.finer(countSql);
		m_count = -1;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(countSql, null);
			setParameters (pstmt, true);
			rs = pstmt.executeQuery();

			if (rs.next())
				m_count = rs.getInt(1);

		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, countSql, e);
			m_count = -2;
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (log.isLoggable(Level.FINE))
			log.fine("#" + m_count + " - " + (System.currentTimeMillis()-start) + "ms");
		
		return true;
	}	//	testCount
	
	
	private void detachPivot()
	{
		pivot.setVisible(false);
		
		/**Get Now Pivot Window Parameter*/
		TabularPivotField[] fields = pivotModel.getFields();
		String dataFieldOrient = pivot.getDataFieldOrient();
		boolean isGrandTotalForColumns = pivot.isGrandTotalForColumns();
		boolean isGrandTotalForRows = pivot.isGrandTotalForRows();
		boolean isAutoWrap =  pivot.isAutowrap();

		
		/**Create Pivot(Auto refresh Pivot Window Parameter by new TabularPivotModel)*/
		pivotModel = new TabularPivotModel(new ArrayList<List<Object>>(), columnNames);

		/**Reset to Now Pivot Window Parameter*/
		for(int i = 0; i < fields.length; i++)
		{
			pivotModel.setFieldType(fields[i].getFieldName(), fields[i].getType());
		}
		pivot.setDataFieldOrient(dataFieldOrient);
		pivot.setPageSize(Integer.parseInt(pageSize.getValue().toString()));
		pivot.setGrandTotalForColumns(isGrandTotalForColumns);
		pivot.setGrandTotalForColumns(isGrandTotalForRows);
		pivot.setAutowrap(isAutoWrap);
		
		pivot.setModel(pivotModel);
		pivotFieldControl.setModel(pivotModel);

	}
	
	
	protected void prepareTable() 
	{		
		List<PivotWindowFieldInfo> list = new ArrayList<PivotWindowFieldInfo>();
		
		int i = 0;
		for(MPivotWindowField infoColumn : m_PivotWindowFields) 
		{						
			if (infoColumn.isDisplayed(Env.getCtx(), p_WindowNo)) 
			{
				PivotWindowFieldInfo columnInfo = null;
				String colSQL = infoColumn.getSelectClause();
				if (! colSQL.toUpperCase().contains(" AS "))
					colSQL += " AS " + infoColumn.getColumnName();
				if (infoColumn.getAD_Reference_ID() == DisplayType.ID) 
				{
					columnInfo = new PivotWindowFieldInfo(infoColumn.get_Translation("Name"), colSQL, DisplayType.getClass(infoColumn.getAD_Reference_ID(), true));
				}
				else if (DisplayType.isLookup(infoColumn.getAD_Reference_ID()))
				{
					if (infoColumn.getAD_Reference_ID() == DisplayType.List)
					{
						WEditor editor = null;
						editor = WebEditorFactory.getEditor(gridFields.get(i), true);
				        editor.setMandatory(false);
				        editor.setReadWrite(false);
				        editorMap.put(colSQL, editor);
						columnInfo = new PivotWindowFieldInfo(infoColumn.get_Translation("Name"), colSQL, ValueNamePair.class, (String)null);
					}
					else
					{
						columnInfo = createLookupColumnInfo(tableInfos, gridFields.get(i), infoColumn);
					}					
				}
				else  
				{
					columnInfo = new PivotWindowFieldInfo(infoColumn.get_Translation("Name"), colSQL, DisplayType.getClass(infoColumn.getAD_Reference_ID(), true));
				}
				columnInfo.setColDescription(infoColumn.get_Translation("Description"));
				columnInfo.setGridField(gridFields.get(i));
				list.add(columnInfo);
				
			}		
			i++;
		}
		
		pivotWindowFieldsInfo = list.toArray(new PivotWindowFieldInfo[0]);
		
		prepareTable(pivotWindowFieldsInfo, m_pivotWindow.getFromClause(), p_whereClause, "");		
	}
	

	/** Main SQL Statement      */
	protected String              m_sqlMain;
	/** Count SQL Statement		*/
	protected String              m_sqlCount;
	
	
	protected void prepareTable(PivotWindowFieldInfo[] fieldsInfo, String from, String where,String orderBy) 
	{
		m_sqlMain = prepareTable(fieldsInfo, from, where, false, p_tableName ,false);
		m_sqlCount = "SELECT COUNT(*) FROM " + from + " WHERE " + where;
		
		if (m_sqlMain.indexOf("@") >= 0) 
		{
			String sql = Env.parseContext(Env.getCtx(), p_WindowNo, m_sqlMain, true);
			if (sql == null || sql.length() == 0) {
				log.severe("Failed to parsed sql. sql=" + m_sqlMain);
			} else {
				m_sqlMain = sql;
			}
		}
		
	}

	
	protected PivotWindowFieldInfo createLookupColumnInfo(TableInfo[] tableInfos, GridField gridField, MPivotWindowField infoColumn) 
	{
		String columnName = gridField.getColumnName();
		String validationCode = "";
		MLookupInfo lookupInfo = MLookupFactory.getLookupInfo(Env.getCtx(), p_WindowNo, 0, infoColumn.getAD_Reference_ID(), Env.getLanguage(Env.getCtx()), columnName, infoColumn.getAD_Reference_Value_ID(), false, validationCode);
		String displayColumn = lookupInfo.DisplayColumn;
		
		int index = infoColumn.getSelectClause().indexOf(".");
		if (index == infoColumn.getSelectClause().lastIndexOf("."))
		{
			String synonym = infoColumn.getSelectClause().substring(0, index);
			for(TableInfo tableInfo : tableInfos)
			{
				if (tableInfo.getSynonym() != null && tableInfo.getSynonym().equals(synonym)) 
				{
					if (tableInfo.getTableName().equalsIgnoreCase(lookupInfo.TableName))
					{
						displayColumn = displayColumn.replace(lookupInfo.TableName+".", tableInfo.getSynonym()+".");
						PivotWindowFieldInfo columnInfo = new PivotWindowFieldInfo(infoColumn.get_Translation("Name"), displayColumn, KeyNamePair.class, infoColumn.getSelectClause());
						return columnInfo;
					}
					break;
				}
			}
		}
		
		WEditor editor = null;
        editor = WebEditorFactory.getEditor(gridField, true);
        editor.setMandatory(false);
        editor.setReadWrite(false);

		String colSQL = infoColumn.getSelectClause();
		if (! colSQL.toUpperCase().contains(" AS "))
			colSQL += " AS " + infoColumn.getColumnName();
        editorMap.put(colSQL, editor);
        PivotWindowFieldInfo columnInfo = new PivotWindowFieldInfo(infoColumn.get_Translation("Name"), colSQL, KeyNamePair.class, (String)null);
		return columnInfo;
	}
	
	
	protected String getSQLWhere() 
	{
		StringBuilder builder = new StringBuilder();
		MTable table = MTable.get(Env.getCtx(), m_pivotWindow.getAD_Table_ID());
		if (!hasIsActiveEditor() && table.get_ColumnIndex("IsActive") >=0 ) 
		{
			if (p_whereClause != null && p_whereClause.trim().length() > 0) 
			{
				builder.append(" AND ");
			}
			builder.append(tableInfos[0].getSynonym()).append(".IsActive='Y'");
		}
		int count = 0;
		for(WEditor editor : editors) 
		{
			
			if (!editor.isVisible()) 
				continue;
			
			if (editor instanceof IWhereClauseEditor) {
				String whereClause = ((IWhereClauseEditor) editor).getWhereClause();
				if (whereClause != null && whereClause.trim().length() > 0) {
					count++;
					if (count == 1) {
						if (builder.length() > 0) {
							builder.append(" AND ");
							if (!checkAND.isChecked()) builder.append(" ( ");
						} else if (p_whereClause != null && p_whereClause.trim().length() > 0) {
							builder.append(" AND ");
							if (!checkAND.isChecked()) builder.append(" ( ");
						}	
					} else {
						builder.append(checkAND.isChecked() ? " AND " : " OR ");
					}
					builder.append(whereClause);
				}
			} else if (editor.getGridField() != null && editor.getValue() != null && editor.getValue().toString().trim().length() > 0) {
				MPivotWindowField mInfoColumn = findInfoColumn(editor.getGridField());
				if (mInfoColumn == null || mInfoColumn.getSelectClause().equals("0")) {
					continue;
				}
				String columnName = mInfoColumn.getSelectClause();
				int asIndex = columnName.toUpperCase().lastIndexOf(" AS ");
				if (asIndex > 0) {
					columnName = columnName.substring(0, asIndex);
				}
				
				count++;
				if (count == 1) {
					if (builder.length() > 0) {
						builder.append(" AND ");
						if (!checkAND.isChecked()) builder.append(" ( ");
					} else if (p_whereClause != null && p_whereClause.trim().length() > 0) {
						builder.append(" AND ");
						if (!checkAND.isChecked()) builder.append(" ( ");
					} else if (hasIsActiveEditor() && !checkAND.isChecked()) {
						builder.append(" ( ");
					}
				} else {
					builder.append(checkAND.isChecked() ? " AND " : " OR ");
				}
				
				String columnClause = null;
				if (mInfoColumn.getQueryFunction() != null && mInfoColumn.getQueryFunction().trim().length() > 0) {
					String function = mInfoColumn.getQueryFunction();
					if (function.indexOf("@") >= 0) {
						String s = Env.parseContext(Env.getCtx(), p_WindowNo, function, true, false);
						if (s.length() == 0) {
							log.log(Level.SEVERE, "Failed to parse query function. " + function);
						} else {
							function = s;
						}
					}
					if (function.indexOf("?") >= 0) {
						columnClause = function.replaceFirst("[?]", columnName);
					} else {
						columnClause = function+"("+columnName+")";
					}
				} else {
					columnClause = columnName;
				}
				builder.append(columnClause)
					   .append(" ")
					   .append(mInfoColumn.getQueryOperator())
					   .append(" ?");				
			}
		}	
		if (count > 0 && !checkAND.isChecked()) {
			builder.append(" ) ");
		}
		String sql = builder.toString();
		if (sql.indexOf("@") >= 0) {
			sql = Env.parseContext(Env.getCtx(), p_WindowNo, sql, true, true);
		}
		
		return sql;
	}
	
	
	protected MPivotWindowField findInfoColumn(GridField gridField) 
	{
		for(int i = 0; i < gridFields.size(); i++) {
			if (gridFields.get(i) == gridField) {
				return m_PivotWindowFields[i];
			}
		}
		return null;
	}
	
	
	/** Return true if there is an 'IsActive' criteria */
	boolean hasIsActiveEditor() 
	{
		for (WEditor editor : editors) 
		{
			if (editor.getGridField() != null && "IsActive".equals(editor.getGridField().getColumnName())) {
				return true;
			}
		}
		return false;
	}
	
	
    public String prepareTable(PivotWindowFieldInfo[] fieldsInfo, String from, String where, boolean multiSelection, String tableName,boolean addAccessSQL)
    {

    	
        int columnIndex = 0;
        StringBuilder sql = new StringBuilder ("SELECT ");

        //  add columns & sql
        for (columnIndex = 0; columnIndex < fieldsInfo.length; columnIndex++)
        {
            //  create sql
            if (columnIndex > 0)
            {
                sql.append(", ");
            }
            sql.append(fieldsInfo[columnIndex].getColSQL());

            //  adding ID column
            if (fieldsInfo[columnIndex].isKeyPairCol())
            {
                sql.append(",").append(fieldsInfo[columnIndex].getKeyPairColSQL());
            }
            
        }

        sql.append( " FROM ").append(from);
       	sql.append(" WHERE ").append(where);

        if (from.length() == 0)
        {
            return sql.toString();
        }
        //
        if (addAccessSQL)
        {
            String finalSQL = MRole.getDefault().addAccessSQL(sql.toString(),
                                                        tableName,
                                                        MRole.SQL_FULLYQUALIFIED,
                                                        MRole.SQL_RO);

//            logger.finest(finalSQL);

            return finalSQL;
        }
        else
        {
            return sql.toString();
        }
    }   // prepareTable
    

    protected String buildDataSQL() 
    {
		String dataSql;
		String dynWhere = getSQLWhere();
        StringBuilder sql = new StringBuilder (m_sqlMain);
        if (dynWhere.length() > 0)
            sql.append(dynWhere);   //  includes first AND
        
        if (sql.toString().trim().endsWith("WHERE")) {
        	int index = sql.lastIndexOf(" WHERE");
        	sql.delete(index, sql.length());
        }

        dataSql = Msg.parseTranslation(Env.getCtx(), sql.toString());    //  Variables
        dataSql = MRole.getDefault().addAccessSQL(dataSql, p_tableName, MRole.SQL_FULLYQUALIFIED, MRole.SQL_RO);
 
		return dataSql;
	}
  
    
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException 
	{
		
		// init collection to save current parameter value 
		if (prevParameterValues == null)
		{
			prevParameterValues = new ArrayList<Object> ();
			prevQueryOperators = new ArrayList<String> ();
			prevRefParmeterEditor = new ArrayList<WEditor>(); 
		}else{
			prevParameterValues.clear();
			prevQueryOperators.clear();
			prevRefParmeterEditor.clear();
		}

		int parameterIndex = 0;
		for(WEditor editor : editors) 
		{
			if (!editor.isVisible())
				continue;
			
			if (editor.getGridField() != null && editor.getValue() != null && editor.getValue().toString().trim().length() > 0) 
			{
				MPivotWindowField mInfoColumn = findInfoColumn(editor.getGridField());
				if (mInfoColumn == null || mInfoColumn.getSelectClause().equals("0")) {
					continue;
				}
				Object value = editor.getValue();
				parameterIndex++;
				prevParameterValues.add(value);
				prevQueryOperators.add(mInfoColumn.getQueryOperator());
				prevRefParmeterEditor.add(editor);
				setParameter (pstmt, parameterIndex, value, mInfoColumn.getQueryOperator());
			}
		}

	}
	
	/**
	 * set parameter for statement. 
	 * not need check null for value
	 * @param pstmt
	 * @param parameterIndex
	 * @param value
	 * @param queryOperator
	 * @throws SQLException
	 */
	protected void setParameter (PreparedStatement pstmt, int parameterIndex, Object value, String queryOperator) throws SQLException
	{
		if (value instanceof Boolean) 
		{					
			pstmt.setString(parameterIndex, ((Boolean) value).booleanValue() ? "Y" : "N");
			
		} else if (value instanceof String) {
			
			if (queryOperator.equals(X_AD_InfoColumn.QUERYOPERATOR_Like)) 
			{
				StringBuilder valueStr = new StringBuilder(value.toString().toUpperCase());
				if (!valueStr.toString().endsWith("%"))
					valueStr.append("%");
				
				pstmt.setString(parameterIndex, valueStr.toString());
			} else {
				
				pstmt.setString(parameterIndex, (String)value);
			}
			
		} else {
				pstmt.setObject(parameterIndex, value);
		}
	}

	
	protected  ArrayList<Object> readData(ResultSet rs, PivotWindowFieldInfo[] fieldsInfo) throws SQLException 
	{

		int colOffset = 1;  //  columns start with 1
		ArrayList<Object> data = new ArrayList<Object>();
		for (int col = 0; col < fieldsInfo.length; col++)
		{
			Object value = null;
			Class<?> c = fieldsInfo[col].getColClass();
			int colIndex = col + colOffset;
			if (c == Boolean.class)
				value = new Boolean("Y".equals(rs.getString(colIndex)));
			else if (c == Timestamp.class)
				value = rs.getTimestamp(colIndex);
			else if (c == BigDecimal.class)
				value = rs.getBigDecimal(colIndex);
			else if (c == Double.class)
				value = new Double(rs.getDouble(colIndex));
			else if (c == Integer.class)
				value = new Integer(rs.getInt(colIndex));
			else if (c == KeyNamePair.class)
			{
				if (fieldsInfo[col].isKeyPairCol())
				{
					String display = rs.getString(colIndex);
					int key = rs.getInt(colIndex+1);
					if (! rs.wasNull()) {
						value = new KeyNamePair(key, display);
					}
					colOffset++;
				}
				else
				{
					int key = rs.getInt(colIndex);
					if (! rs.wasNull()) {
						WEditor editor = editorMap.get(fieldsInfo[col].getColSQL()); // rework this, it will fail
						if (editor != null)
						{
							editor.setValue(key);
							value = new KeyNamePair(key, editor.getDisplayTextForGridView(key));
						}
						else
						{
							value = new KeyNamePair(key, Integer.toString(key));
						}
					}
				}
			}
			else if (c == ValueNamePair.class)
			{
				String key = rs.getString(colIndex);
				WEditor editor = editorMap.get(fieldsInfo[col].getColSQL());
				if (editor != null)
				{
					value = new ValueNamePair(key, editor.getDisplayTextForGridView(key));
				}
				else
				{
					value = new ValueNamePair(key, key);
				}
			}
			else
			{
				value = rs.getString(colIndex);
			}
			data.add(value);
		}

		return data;
	}
	
	public MPivotWindow getMPivotWindow()
	{
		return m_pivotWindow;
	}
	
}
