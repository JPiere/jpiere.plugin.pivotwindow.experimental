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
package jpiere.plugin.pivotwindow.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.AccessSqlParser;
import org.compiere.model.AccessSqlParser.TableInfo;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 * @author Hideaki Hagiwara
 *
 */
public class CalloutPivotWindow implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) 
	{
		if (mTab.getTableName().equals("JP_PivotWindowField"))
		{
			if (mField.getColumnName().equals("AD_Element_ID"))
				return element(mTab, value);
			else if (mField.getColumnName().equals("AD_Reference_ID"))
				return reference(mTab, value);
			else 
				return "";
		}else if (mTab.getTableName().equals("JP_PivotWindow")){
			return table(mTab, value);
		}else{
			return "";
		}
	}
	

	private String reference(GridTab mTab, Object value) {
		if (value != null) 
		{
			int id = ((Number)value).intValue();
			if (id > 0) 
			{
				I_JP_PivotWindowField infoColumn = GridTabWrapper.create(mTab, I_JP_PivotWindowField.class);
				setQueryOption(id, infoColumn);
			}
		}
		return null;
	}

	
	private void setQueryOption(int AD_Reference_ID, I_JP_PivotWindowField pivotWindowField) 
	{
		if (DisplayType.isText(AD_Reference_ID)) 
		{
			pivotWindowField.setQueryOperator(X_JP_PivotWindowField.QUERYOPERATOR_Like);
			pivotWindowField.setQueryFunction("Upper");
		} else {
			pivotWindowField.setQueryOperator(X_JP_PivotWindowField.QUERYOPERATOR_Eq);
		}
		
		if (AD_Reference_ID == DisplayType.Date) 
		{
			pivotWindowField.setQueryFunction("Trunc");
		}
	}

	
	private String table(GridTab mTab, Object value) 
	{
		if (value != null) 
		{			
			int id = ((Number)value).intValue();
			if (id > 0) 
			{
				I_JP_PivotWindow pivotWindow = GridTabWrapper.create(mTab, I_JP_PivotWindow.class);
				if (pivotWindow.getFromClause() == null || pivotWindow.getFromClause().trim().length() == 0) 
				{
					MTable table = MTable.get(Env.getCtx(), id);
					if (table != null) 
					{
						pivotWindow.setFromClause(table.getTableName() + " a");
						if (pivotWindow.getName() == null || pivotWindow.getName().trim().length() == 0) 
						{
							pivotWindow.setName(table.getName());
						}
					}						
				}
			}
		}
		return null;
	}
	

	protected String element(GridTab mTab, Object value) 
	{		
		if (value != null) 
		{			
			int id = ((Number)value).intValue();
			if (id > 0) 
			{
				I_JP_PivotWindowField pivotWindowField = GridTabWrapper.create(mTab, I_JP_PivotWindowField.class);
				M_Element element = new M_Element(Env.getCtx(), id, (String)null);
				pivotWindowField.setColumnName(element.getColumnName());
				pivotWindowField.setDescription(element.getDescription());
				pivotWindowField.setHelp(element.getHelp());
				pivotWindowField.setName(element.getName());
				if (pivotWindowField.getSelectClause() == null || pivotWindowField.getSelectClause().trim().length() == 0) 
				{
					String fromClause = pivotWindowField.getJP_PivotWindow().getFromClause();
					AccessSqlParser parser = new AccessSqlParser("SELECT * FROM " + fromClause);
					TableInfo[] tableInfos = parser.getTableInfo(0);
					Map<String, MTable> map = new HashMap<String, MTable>();
					for(TableInfo ti : tableInfos) 
					{
						String alias = ti.getSynonym();
						String tableName = ti.getTableName();
						
						MTable table = map.get(tableName);
						if (table == null) 
						{
							table = MTable.get(Env.getCtx(), tableName);
							if (table == null) continue;
							map.put(tableName, table);
						}
						MColumn col = table.getColumn(element.getColumnName());
						if (col != null) 
						{
							pivotWindowField.setSelectClause(alias+"."+element.getColumnName());
							pivotWindowField.setAD_Reference_ID(col.getAD_Reference_ID());
							pivotWindowField.setAD_Reference_Value_ID(col.getAD_Reference_Value_ID());
							pivotWindowField.setAD_Val_Rule_ID(col.getAD_Val_Rule_ID());
							if ((col.isSelectionColumn() || col.isIdentifier()) && !col.isKey()) 
							{								
								pivotWindowField.setIsQueryCriteria(true);
								setQueryOption(pivotWindowField.getAD_Reference_ID(), pivotWindowField);
							}
							break;
						}
					}
				}
			}
		}
		return "";
	}

}
