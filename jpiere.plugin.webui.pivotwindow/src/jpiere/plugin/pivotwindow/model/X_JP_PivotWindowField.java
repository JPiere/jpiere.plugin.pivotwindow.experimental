/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package jpiere.plugin.pivotwindow.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for JP_PivotWindowField
 *  @author iDempiere (generated) 
 *  @version Release 4.1 - $Id$ */
public class X_JP_PivotWindowField extends PO implements I_JP_PivotWindowField, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170612L;

    /** Standard Constructor */
    public X_JP_PivotWindowField (Properties ctx, int JP_PivotWindowField_ID, String trxName)
    {
      super (ctx, JP_PivotWindowField_ID, trxName);
      /** if (JP_PivotWindowField_ID == 0)
        {
			setAD_Reference_ID (0);
			setColumnName (null);
			setIsCentrallyMaintained (true);
// Y
			setIsDisplayed (true);
// Y
			setIsMandatory (false);
			setIsQueryCriteria (false);
// N
			setJP_PivotWindowField_ID (0);
			setJP_PivotWindow_ID (0);
			setName (null);
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM JP_PivotWindowField WHERE JP_PivotWindow_ID=@JP_PivotWindow_ID@
        } */
    }

    /** Load Constructor */
    public X_JP_PivotWindowField (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_JP_PivotWindowField[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Element)MTable.get(getCtx(), org.compiere.model.I_AD_Element.Table_Name)
			.getPO(getAD_Element_ID(), get_TrxName());	}

	/** Set System Element.
		@param AD_Element_ID 
		System Element enables the central maintenance of column description and help.
	  */
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System Element.
		@return System Element enables the central maintenance of column description and help.
	  */
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Reference.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_Value_ID(), get_TrxName());	}

	/** Set Reference Key.
		@param AD_Reference_Value_ID 
		Required to specify, if data type is Table or List
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get Reference Key.
		@return Required to specify, if data type is Table or List
	  */
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Val_Rule)MTable.get(getCtx(), org.compiere.model.I_AD_Val_Rule.Table_Name)
			.getPO(getAD_Val_Rule_ID(), get_TrxName());	}

	/** Set Dynamic Validation.
		@param AD_Val_Rule_ID 
		Dynamic Validation Rule
	  */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamic Validation.
		@return Dynamic Validation Rule
	  */
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
	}

	/** Set Default Logic.
		@param DefaultValue 
		Default value hierarchy, separated by ;
	  */
	public void setDefaultValue (String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	/** Get Default Logic.
		@return Default value hierarchy, separated by ;
	  */
	public String getDefaultValue () 
	{
		return (String)get_Value(COLUMNNAME_DefaultValue);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Display Logic.
		@param DisplayLogic 
		If the Field is displayed, the result determines if the field is actually displayed
	  */
	public void setDisplayLogic (String DisplayLogic)
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

	/** Get Display Logic.
		@return If the Field is displayed, the result determines if the field is actually displayed
	  */
	public String getDisplayLogic () 
	{
		return (String)get_Value(COLUMNNAME_DisplayLogic);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Centrally maintained.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Centrally maintained.
		@return Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained () 
	{
		Object oo = get_Value(COLUMNNAME_IsCentrallyMaintained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Query Criteria.
		@param IsQueryCriteria 
		The column is also used as a query criteria
	  */
	public void setIsQueryCriteria (boolean IsQueryCriteria)
	{
		set_Value (COLUMNNAME_IsQueryCriteria, Boolean.valueOf(IsQueryCriteria));
	}

	/** Get Query Criteria.
		@return The column is also used as a query criteria
	  */
	public boolean isQueryCriteria () 
	{
		Object oo = get_Value(COLUMNNAME_IsQueryCriteria);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set JP_PivotWindowField.
		@param JP_PivotWindowField_ID JP_PivotWindowField	  */
	public void setJP_PivotWindowField_ID (int JP_PivotWindowField_ID)
	{
		if (JP_PivotWindowField_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindowField_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindowField_ID, Integer.valueOf(JP_PivotWindowField_ID));
	}

	/** Get JP_PivotWindowField.
		@return JP_PivotWindowField	  */
	public int getJP_PivotWindowField_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_PivotWindowField_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set JP_PivotWindowField_UU.
		@param JP_PivotWindowField_UU JP_PivotWindowField_UU	  */
	public void setJP_PivotWindowField_UU (String JP_PivotWindowField_UU)
	{
		set_ValueNoCheck (COLUMNNAME_JP_PivotWindowField_UU, JP_PivotWindowField_UU);
	}

	/** Get JP_PivotWindowField_UU.
		@return JP_PivotWindowField_UU	  */
	public String getJP_PivotWindowField_UU () 
	{
		return (String)get_Value(COLUMNNAME_JP_PivotWindowField_UU);
	}

	public I_JP_PivotWindow getJP_PivotWindow() throws RuntimeException
    {
		return (I_JP_PivotWindow)MTable.get(getCtx(), I_JP_PivotWindow.Table_Name)
			.getPO(getJP_PivotWindow_ID(), get_TrxName());	}

	/** Set JP_PivotWindow.
		@param JP_PivotWindow_ID JP_PivotWindow	  */
	public void setJP_PivotWindow_ID (int JP_PivotWindow_ID)
	{
		if (JP_PivotWindow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindow_ID, Integer.valueOf(JP_PivotWindow_ID));
	}

	/** Get JP_PivotWindow.
		@return JP_PivotWindow	  */
	public int getJP_PivotWindow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_PivotWindow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mandatory Logic.
		@param MandatoryLogic Mandatory Logic	  */
	public void setMandatoryLogic (String MandatoryLogic)
	{
		set_Value (COLUMNNAME_MandatoryLogic, MandatoryLogic);
	}

	/** Get Mandatory Logic.
		@return Mandatory Logic	  */
	public String getMandatoryLogic () 
	{
		return (String)get_Value(COLUMNNAME_MandatoryLogic);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Query Function.
		@param QueryFunction 
		Database function for query
	  */
	public void setQueryFunction (String QueryFunction)
	{
		set_Value (COLUMNNAME_QueryFunction, QueryFunction);
	}

	/** Get Query Function.
		@return Database function for query
	  */
	public String getQueryFunction () 
	{
		return (String)get_Value(COLUMNNAME_QueryFunction);
	}

	/** QueryOperator AD_Reference_ID=200061 */
	public static final int QUERYOPERATOR_AD_Reference_ID=200061;
	/** Like = Like */
	public static final String QUERYOPERATOR_Like = "Like";
	/** = = = */
	public static final String QUERYOPERATOR_Eq = "=";
	/** > = > */
	public static final String QUERYOPERATOR_Gt = ">";
	/** >= = >= */
	public static final String QUERYOPERATOR_GtEq = ">=";
	/** < = < */
	public static final String QUERYOPERATOR_Le = "<";
	/** <= = <= */
	public static final String QUERYOPERATOR_LeEq = "<=";
	/** != = != */
	public static final String QUERYOPERATOR_NotEq = "!=";
	/** Set Query Operator.
		@param QueryOperator 
		Operator for database query
	  */
	public void setQueryOperator (String QueryOperator)
	{

		set_Value (COLUMNNAME_QueryOperator, QueryOperator);
	}

	/** Get Query Operator.
		@return Operator for database query
	  */
	public String getQueryOperator () 
	{
		return (String)get_Value(COLUMNNAME_QueryOperator);
	}

	/** Set Sql SELECT.
		@param SelectClause 
		SQL SELECT clause
	  */
	public void setSelectClause (String SelectClause)
	{
		set_Value (COLUMNNAME_SelectClause, SelectClause);
	}

	/** Get Sql SELECT.
		@return SQL SELECT clause
	  */
	public String getSelectClause () 
	{
		return (String)get_Value(COLUMNNAME_SelectClause);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Selection Column Sequence.
		@param SeqNoSelection 
		Selection Column Sequence
	  */
	public void setSeqNoSelection (int SeqNoSelection)
	{
		set_Value (COLUMNNAME_SeqNoSelection, Integer.valueOf(SeqNoSelection));
	}

	/** Get Selection Column Sequence.
		@return Selection Column Sequence
	  */
	public int getSeqNoSelection () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNoSelection);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}