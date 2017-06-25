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

/** Generated Model for JP_PivotWindowDefault
 *  @author iDempiere (generated) 
 *  @version Release 4.1 - $Id$ */
public class X_JP_PivotWindowDefault extends PO implements I_JP_PivotWindowDefault, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170612L;

    /** Standard Constructor */
    public X_JP_PivotWindowDefault (Properties ctx, int JP_PivotWindowDefault_ID, String trxName)
    {
      super (ctx, JP_PivotWindowDefault_ID, trxName);
      /** if (JP_PivotWindowDefault_ID == 0)
        {
			setIsAutoWrapJP (false);
			setIsDefault (false);
// N
			setIsDisplayTotalColumnJP (false);
// N
			setIsDisplayTotalRowJP (false);
// N
			setJP_DataFieldOrientation (null);
// R
			setJP_PageSize (0);
// 20
			setJP_PivotWindowDefault_ID (0);
			setJP_PivotWindow_ID (0);
			setName (null);
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM JP_PivotWindowDefault WHERE JP_PivotWindow_ID=@JP_PivotWindow_ID@
        } */
    }

    /** Load Constructor */
    public X_JP_PivotWindowDefault (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_JP_PivotWindowDefault[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Auto Wrap.
		@param IsAutoWrapJP Auto Wrap	  */
	public void setIsAutoWrapJP (boolean IsAutoWrapJP)
	{
		set_Value (COLUMNNAME_IsAutoWrapJP, Boolean.valueOf(IsAutoWrapJP));
	}

	/** Get Auto Wrap.
		@return Auto Wrap	  */
	public boolean isAutoWrapJP () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoWrapJP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Display total of columns .
		@param IsDisplayTotalColumnJP Display total of columns 	  */
	public void setIsDisplayTotalColumnJP (boolean IsDisplayTotalColumnJP)
	{
		set_Value (COLUMNNAME_IsDisplayTotalColumnJP, Boolean.valueOf(IsDisplayTotalColumnJP));
	}

	/** Get Display total of columns .
		@return Display total of columns 	  */
	public boolean isDisplayTotalColumnJP () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayTotalColumnJP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Display total of Rows .
		@param IsDisplayTotalRowJP Display total of Rows 	  */
	public void setIsDisplayTotalRowJP (boolean IsDisplayTotalRowJP)
	{
		set_Value (COLUMNNAME_IsDisplayTotalRowJP, Boolean.valueOf(IsDisplayTotalRowJP));
	}

	/** Get Display total of Rows .
		@return Display total of Rows 	  */
	public boolean isDisplayTotalRowJP () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayTotalRowJP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Row = R */
	public static final String JP_DATAFIELDORIENTATION_Row = "R";
	/** Column = C */
	public static final String JP_DATAFIELDORIENTATION_Column = "C";
	/** Set Data field orientation.
		@param JP_DataFieldOrientation Data field orientation	  */
	public void setJP_DataFieldOrientation (String JP_DataFieldOrientation)
	{

		set_Value (COLUMNNAME_JP_DataFieldOrientation, JP_DataFieldOrientation);
	}

	/** Get Data field orientation.
		@return Data field orientation	  */
	public String getJP_DataFieldOrientation () 
	{
		return (String)get_Value(COLUMNNAME_JP_DataFieldOrientation);
	}

	/** Set Page Size.
		@param JP_PageSize Page Size	  */
	public void setJP_PageSize (int JP_PageSize)
	{
		set_Value (COLUMNNAME_JP_PageSize, Integer.valueOf(JP_PageSize));
	}

	/** Get Page Size.
		@return Page Size	  */
	public int getJP_PageSize () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JP_PageSize);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set JP_PivotWindowDefault_UU.
		@param JP_PivotWindowDefault_UU JP_PivotWindowDefault_UU	  */
	public void setJP_PivotWindowDefault_UU (String JP_PivotWindowDefault_UU)
	{
		set_ValueNoCheck (COLUMNNAME_JP_PivotWindowDefault_UU, JP_PivotWindowDefault_UU);
	}

	/** Get JP_PivotWindowDefault_UU.
		@return JP_PivotWindowDefault_UU	  */
	public String getJP_PivotWindowDefault_UU () 
	{
		return (String)get_Value(COLUMNNAME_JP_PivotWindowDefault_UU);
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