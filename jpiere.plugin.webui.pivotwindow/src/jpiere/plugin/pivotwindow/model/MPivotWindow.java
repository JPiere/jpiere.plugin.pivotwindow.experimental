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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.AccessSqlParser;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.model.AccessSqlParser.TableInfo;

public class MPivotWindow extends X_JP_PivotWindow {
	
	private MPivotWindowField[] m_pivotFields = null;
	private MPivotWindowDefault[] m_pivotDefaults = null;
	
	
	public MPivotWindow(Properties ctx, int JP_PivotWindow_ID, String trxName) 
	{
		super(ctx, JP_PivotWindow_ID, trxName);
	}
	
	
	public MPivotWindow(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	
	static public MPivotWindow get(int JP_PivotWindow_ID, String trxName)
	{
		
		return new MPivotWindow(Env.getCtx(),JP_PivotWindow_ID, trxName);
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		AccessSqlParser parser = new AccessSqlParser("SELECT * FROM " + getFromClause());
		TableInfo[] tableInfos = parser.getTableInfo(0);
		if (tableInfos == null || tableInfos.length == 0) {
			log.saveError("ParseFromClauseError", "Failed to parse from clause");
			return false;
		}
		
		
		// evaluate need valid
		boolean isNeedValid = is_new() || is_ValueChanged (I_JP_PivotWindow.COLUMNNAME_AD_Table_ID) || is_ValueChanged (I_JP_PivotWindow.COLUMNNAME_WhereClause) ||
								is_ValueChanged (I_JP_PivotWindow.COLUMNNAME_FromClause) ;
		
		// valid config 
		if (isNeedValid){
			validate();
		}
		
		return true;
	}
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) 
	{
		if (!success)
			return success;

		return true;
	}

	
	public MPivotWindowField[] getPivotWindowFields(boolean requery, boolean checkDisplay) 
	{
		if ((this.m_pivotFields != null) && (!requery)) {
			set_TrxName(this.m_pivotFields, get_TrxName());
			return this.m_pivotFields;
		}
		if (checkDisplay) {
			List<MPivotWindowField> list = new Query(getCtx(), MPivotWindowField.Table_Name, "JP_PivotWindow_ID=? AND IsDisplayed='Y'", get_TrxName())
				.setParameters(get_ID())
				.setOrderBy("SeqNo")
				.list();
			this.m_pivotFields = list.toArray(new MPivotWindowField[list.size()]);
		} else {
			List<MPivotWindowField> list = new Query(getCtx(), MPivotWindowField.Table_Name, "JP_PivotWindow_ID=?", get_TrxName())
				.setParameters(get_ID())
				.setOrderBy("SeqNo")
				.list();
			this.m_pivotFields = list.toArray(new MPivotWindowField[list.size()]);
		}

		return this.m_pivotFields;
	}
	
	
	public MPivotWindowField[] getPivotWindowFields() 
	{
		Query query = new Query(getCtx(), MTable.get(getCtx(), I_JP_PivotWindowField.Table_ID), I_JP_PivotWindowField.COLUMNNAME_JP_PivotWindow_ID+"=?", get_TrxName());
		List<MPivotWindowField> list = query.setParameters(getJP_PivotWindow_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("SeqNo, JP_PivotWindowField_ID")
				.list();
		return list.toArray(new MPivotWindowField[0]);
	}
	
	
	public MPivotWindowDefault[] getPivotWindowDefaults() 
	{
		if(m_pivotDefaults != null)
			return m_pivotDefaults;
		
		Query query = new Query(getCtx(), MTable.get(getCtx(), I_JP_PivotWindowDefault.Table_ID), I_JP_PivotWindowDefault.COLUMNNAME_JP_PivotWindow_ID+"=?", get_TrxName());
		List<MPivotWindowDefault> list = query.setParameters(getJP_PivotWindow_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("IsDefault DESC, SeqNo, JP_PivotWindowDefault_ID")
				.list();
		
		m_pivotDefaults = list.toArray(new MPivotWindowDefault[0]);
		return m_pivotDefaults;
	}
	
	
	public void validate ()
	{		
		// default, before complete check is invalid
		this.setIsValid(false);		
		
		// add DISTINCT clause
		StringBuilder builder = new StringBuilder("SELECT ");

		
		MPivotWindowField[] pivotWindowFields = this.getPivotWindowFields();
		// none column make this invalid
		if (pivotWindowFields.length == 0){
			return;
		}
		
		// build select clause
		for (int columnIndex = 0; columnIndex < pivotWindowFields.length; columnIndex++) {
			if (columnIndex > 0)
            {
                builder.append(", ");
            }
			builder.append(pivotWindowFields[columnIndex].getSelectClause());
		}
		
		// build from clause
		builder.append( " FROM ").append(this.getFromClause());
		
		// build where clause add (1=2) because not need get result, decrease load 
		if (this.getWhereClause() != null && this.getWhereClause().trim().length() > 0) {
			builder.append(" WHERE (1=2) AND (").append(this.getWhereClause()).append(")");
		} else {
			builder.append(" WHERE 1=2");
		}
		
		// replace env value by dummy value
		while(builder.indexOf("@") >= 0) {
			int start = builder.indexOf("@");
			int end = builder.indexOf("@", start+1);
			if (start >=0 && end > start) {
				builder.replace(start, end+1, "0");
			} else {
				break;
			}
		}
		
		// try run sql
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(builder.toString(), get_TrxName());
			pstmt.executeQuery();
		}catch (Exception ex){
			log.log(Level.WARNING, ex.getMessage());
			return;
		} finally {
			DB.close(pstmt);
		}
		
		// valid state
		this.setIsValid(true);		
	}
}
