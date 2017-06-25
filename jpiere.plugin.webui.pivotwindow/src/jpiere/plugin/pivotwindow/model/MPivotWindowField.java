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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.M_Element;
import org.compiere.util.Env;

public class MPivotWindowField extends X_JP_PivotWindowField {
	
	MPivotWindow m_parent = null;
	
	public MPivotWindowField(Properties ctx, int JP_PivotWindowField_ID, String trxName) 
	{
		super(ctx, JP_PivotWindowField_ID, trxName);
	}
	
	public MPivotWindowField(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	public MPivotWindow getParent()
	{
		if (m_parent == null)
			m_parent = new MPivotWindow(getCtx(), getJP_PivotWindow_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
	static public MPivotWindowField get(int JP_PivotWindowField_ID)//TODO キャッシュ化
	{
		return new MPivotWindowField(Env.getCtx(), JP_PivotWindowField_ID, null);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		// Sync Terminology
		if ((newRecord || is_ValueChanged ("AD_Element_ID")) 
			&& getAD_Element_ID() != 0 && isCentrallyMaintained())
		{
			M_Element element = new M_Element (getCtx(), getAD_Element_ID (), get_TrxName());
			setName (element.getName());
		}
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) 
	{
		if (!success)
			return success;
	
		// evaluate need valid
		boolean isNeedValid = newRecord || is_ValueChanged (MPivotWindowField.COLUMNNAME_SelectClause);
		
		// call valid of parrent
		if (isNeedValid){
			getParent().validate();
			getParent().saveEx(get_TrxName());
		}
				
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * when delete record, call valid from parent to set state
	 * when delete all, valid state is false
	 * when delete a wrong column can make valid state to true
	 */
	@Override
	protected boolean afterDelete(boolean success) {
		getParent().validate();
		getParent().saveEx(get_TrxName());
		return super.afterDelete(success);
	}
	
	/**
	 * @param ctx
	 * @param windowNo
	 * @return boolean
	 */
	public boolean isDisplayed(final Properties ctx, final int windowNo) 
	{
		if (!isDisplayed())
			return false;
		
		if (getDisplayLogic() == null || getDisplayLogic().trim().length() == 0)
			return true;
		
		/**Comment out evaluate logic. Because Pivot Window need not evaluate at Model class */
//		Evaluatee evaluatee = new Evaluatee() {
//			public String get_ValueAsString(String variableName) {
//				return Env.getContext (ctx, windowNo, variableName, true);
//			}
//		};
//		
//		boolean retValue = Evaluator.evaluateLogic(evaluatee, getDisplayLogic());
//		if (log.isLoggable(Level.FINEST)) log.finest(getName() 
//					+ " (" + getDisplayLogic() + ") => " + retValue);
//		return retValue;
		
		return true;
	}
}
