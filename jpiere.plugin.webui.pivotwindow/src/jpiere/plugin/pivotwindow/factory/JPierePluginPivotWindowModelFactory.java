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

package jpiere.plugin.pivotwindow.factory;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;

import jpiere.plugin.pivotwindow.model.MPivotWindow;
import jpiere.plugin.pivotwindow.model.MPivotWindowDefault;
import jpiere.plugin.pivotwindow.model.MPivotWindowDefaultField;
import jpiere.plugin.pivotwindow.model.MPivotWindowField;

/**
 *  JPiere Plugins Pivot Window Model Factory
 *
 *  JPIERE-0356
 *
 *  @author Hideaki Hagiwara（h.hagiwara@oss-erp.co.jp）
 *
 */
public class JPierePluginPivotWindowModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) 
	{
		if(tableName.startsWith("JP_Pivot"))
		{
			if(tableName.equals(MPivotWindow.Table_Name)){
				return MPivotWindow.class;
			}else if(tableName.equals(MPivotWindowField.Table_Name)){
				return MPivotWindowField.class;
			}else if(tableName.equals(MPivotWindowDefault.Table_Name)){
				return MPivotWindowDefault.class;
			}else if(tableName.equals(MPivotWindowDefaultField.Table_Name)){
				return MPivotWindowDefaultField.class;
			}
		}
		return null;
		
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) 
	{
		if(tableName.startsWith("JP_Pivot"))
		{
			if(tableName.equals(MPivotWindow.Table_Name)){
				return  new MPivotWindow(Env.getCtx(), Record_ID, trxName);
			}else if(tableName.equals(MPivotWindowField.Table_Name)){
				return  new MPivotWindowField(Env.getCtx(), Record_ID, trxName);
			}else if(tableName.equals(MPivotWindowDefault.Table_Name)){
				return  new MPivotWindowDefault(Env.getCtx(), Record_ID, trxName);
			}else if(tableName.equals(MPivotWindowDefaultField.Table_Name)){
				return  new MPivotWindowDefaultField(Env.getCtx(), Record_ID, trxName);
			}
		}
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) 
	{
		if(tableName.startsWith("JP_Pivot"))
		{
			if(tableName.equals(MPivotWindow.Table_Name)){
				return  new MPivotWindow(Env.getCtx(), rs, trxName);
			}else if(tableName.equals(MPivotWindowField.Table_Name)){
				return  new MPivotWindowField(Env.getCtx(), rs, trxName);
			}else if(tableName.equals(MPivotWindowDefault.Table_Name)){
				return  new MPivotWindowDefault(Env.getCtx(), rs, trxName);
			}else if(tableName.equals(MPivotWindowDefaultField.Table_Name)){
				return  new MPivotWindowDefaultField(Env.getCtx(), rs, trxName);
			}
		}
		return null;
	}

}
