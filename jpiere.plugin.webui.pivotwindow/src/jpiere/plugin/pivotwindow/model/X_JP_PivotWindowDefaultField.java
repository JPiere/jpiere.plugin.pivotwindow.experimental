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

/** Generated Model for JP_PivotWindowDefaultField
 *  @author iDempiere (generated) 
 *  @version Release 4.1 - $Id$ */
public class X_JP_PivotWindowDefaultField extends PO implements I_JP_PivotWindowDefaultField, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170612L;

    /** Standard Constructor */
    public X_JP_PivotWindowDefaultField (Properties ctx, int JP_PivotWindowDefaultField_ID, String trxName)
    {
      super (ctx, JP_PivotWindowDefaultField_ID, trxName);
      /** if (JP_PivotWindowDefaultField_ID == 0)
        {
			setJP_PivotFieldPosition (null);
// U
			setJP_PivotWindowDefaultField_ID (0);
			setJP_PivotWindowDefault_ID (0);
			setJP_PivotWindowField_ID (0);
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM JP_PivotWindowDefaultField WHERE JP_PivotWindowDefault_ID=@JP_PivotWindowDefault_ID@
        } */
    }

    /** Load Constructor */
    public X_JP_PivotWindowDefaultField (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_JP_PivotWindowDefaultField[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Rows = R */
	public static final String JP_PIVOTFIELDPOSITION_Rows = "R";
	/** Columns = C */
	public static final String JP_PIVOTFIELDPOSITION_Columns = "C";
	/** Valuse = V */
	public static final String JP_PIVOTFIELDPOSITION_Valuse = "V";
	/** Unused = U */
	public static final String JP_PIVOTFIELDPOSITION_Unused = "U";
	/** Set Predefined Field position .
		@param JP_PivotFieldPosition Predefined Field position 	  */
	public void setJP_PivotFieldPosition (String JP_PivotFieldPosition)
	{

		set_Value (COLUMNNAME_JP_PivotFieldPosition, JP_PivotFieldPosition);
	}

	/** Get Predefined Field position .
		@return Predefined Field position 	  */
	public String getJP_PivotFieldPosition () 
	{
		return (String)get_Value(COLUMNNAME_JP_PivotFieldPosition);
	}

	/** Set JP_PivotWindowDefaultField.
		@param JP_PivotWindowDefaultField_ID JP_PivotWindowDefaultField	  */
	public void setJP_PivotWindowDefaultField_ID (int JP_PivotWindowDefaultField_ID)
	{
		if (JP_PivotWindowDefaultField_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindowDefaultField_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindowDefaultField_ID, Integer.valueOf(JP_PivotWindowDefaultField_ID));
	}

	/** Get JP_PivotWindowDefaultField.
		@return JP_PivotWindowDefaultField	  */
	public int getJP_PivotWindowDefaultField_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_PivotWindowDefaultField_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set JP_PivotWindowDefaultField_UU.
		@param JP_PivotWindowDefaultField_UU JP_PivotWindowDefaultField_UU	  */
	public void setJP_PivotWindowDefaultField_UU (String JP_PivotWindowDefaultField_UU)
	{
		set_ValueNoCheck (COLUMNNAME_JP_PivotWindowDefaultField_UU, JP_PivotWindowDefaultField_UU);
	}

	/** Get JP_PivotWindowDefaultField_UU.
		@return JP_PivotWindowDefaultField_UU	  */
	public String getJP_PivotWindowDefaultField_UU () 
	{
		return (String)get_Value(COLUMNNAME_JP_PivotWindowDefaultField_UU);
	}

	public I_JP_PivotWindowDefault getJP_PivotWindowDefault() throws RuntimeException
    {
		return (I_JP_PivotWindowDefault)MTable.get(getCtx(), I_JP_PivotWindowDefault.Table_Name)
			.getPO(getJP_PivotWindowDefault_ID(), get_TrxName());	}

	/** Set JP_PivotWindowDefault.
		@param JP_PivotWindowDefault_ID JP_PivotWindowDefault	  */
	public void setJP_PivotWindowDefault_ID (int JP_PivotWindowDefault_ID)
	{
		if (JP_PivotWindowDefault_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindowDefault_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_JP_PivotWindowDefault_ID, Integer.valueOf(JP_PivotWindowDefault_ID));
	}

	/** Get JP_PivotWindowDefault.
		@return JP_PivotWindowDefault	  */
	public int getJP_PivotWindowDefault_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_PivotWindowDefault_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_JP_PivotWindowField getJP_PivotWindowField() throws RuntimeException
    {
		return (I_JP_PivotWindowField)MTable.get(getCtx(), I_JP_PivotWindowField.Table_Name)
			.getPO(getJP_PivotWindowField_ID(), get_TrxName());	}

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
}