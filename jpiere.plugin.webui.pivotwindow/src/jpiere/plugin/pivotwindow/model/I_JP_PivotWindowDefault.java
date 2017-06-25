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
package jpiere.plugin.pivotwindow.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for JP_PivotWindowDefault
 *  @author iDempiere (generated) 
 *  @version Release 4.1
 */
@SuppressWarnings("all")
public interface I_JP_PivotWindowDefault 
{

    /** TableName=JP_PivotWindowDefault */
    public static final String Table_Name = "JP_PivotWindowDefault";

    /** AD_Table_ID=1000149 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsAutoWrapJP */
    public static final String COLUMNNAME_IsAutoWrapJP = "IsAutoWrapJP";

	/** Set Auto Wrap	  */
	public void setIsAutoWrapJP (boolean IsAutoWrapJP);

	/** Get Auto Wrap	  */
	public boolean isAutoWrapJP();

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsDisplayTotalColumnJP */
    public static final String COLUMNNAME_IsDisplayTotalColumnJP = "IsDisplayTotalColumnJP";

	/** Set Display total of columns 	  */
	public void setIsDisplayTotalColumnJP (boolean IsDisplayTotalColumnJP);

	/** Get Display total of columns 	  */
	public boolean isDisplayTotalColumnJP();

    /** Column name IsDisplayTotalRowJP */
    public static final String COLUMNNAME_IsDisplayTotalRowJP = "IsDisplayTotalRowJP";

	/** Set Display total of Rows 	  */
	public void setIsDisplayTotalRowJP (boolean IsDisplayTotalRowJP);

	/** Get Display total of Rows 	  */
	public boolean isDisplayTotalRowJP();

    /** Column name JP_DataFieldOrientation */
    public static final String COLUMNNAME_JP_DataFieldOrientation = "JP_DataFieldOrientation";

	/** Set Data field orientation	  */
	public void setJP_DataFieldOrientation (String JP_DataFieldOrientation);

	/** Get Data field orientation	  */
	public String getJP_DataFieldOrientation();

    /** Column name JP_PageSize */
    public static final String COLUMNNAME_JP_PageSize = "JP_PageSize";

	/** Set Page Size	  */
	public void setJP_PageSize (int JP_PageSize);

	/** Get Page Size	  */
	public int getJP_PageSize();

    /** Column name JP_PivotWindowDefault_ID */
    public static final String COLUMNNAME_JP_PivotWindowDefault_ID = "JP_PivotWindowDefault_ID";

	/** Set JP_PivotWindowDefault	  */
	public void setJP_PivotWindowDefault_ID (int JP_PivotWindowDefault_ID);

	/** Get JP_PivotWindowDefault	  */
	public int getJP_PivotWindowDefault_ID();

    /** Column name JP_PivotWindowDefault_UU */
    public static final String COLUMNNAME_JP_PivotWindowDefault_UU = "JP_PivotWindowDefault_UU";

	/** Set JP_PivotWindowDefault_UU	  */
	public void setJP_PivotWindowDefault_UU (String JP_PivotWindowDefault_UU);

	/** Get JP_PivotWindowDefault_UU	  */
	public String getJP_PivotWindowDefault_UU();

    /** Column name JP_PivotWindow_ID */
    public static final String COLUMNNAME_JP_PivotWindow_ID = "JP_PivotWindow_ID";

	/** Set JP_PivotWindow	  */
	public void setJP_PivotWindow_ID (int JP_PivotWindow_ID);

	/** Get JP_PivotWindow	  */
	public int getJP_PivotWindow_ID();

	public I_JP_PivotWindow getJP_PivotWindow() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
