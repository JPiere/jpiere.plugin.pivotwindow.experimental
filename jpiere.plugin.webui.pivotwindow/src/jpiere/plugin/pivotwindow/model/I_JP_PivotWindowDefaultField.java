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

/** Generated Interface for JP_PivotWindowDefaultField
 *  @author iDempiere (generated) 
 *  @version Release 4.1
 */
@SuppressWarnings("all")
public interface I_JP_PivotWindowDefaultField 
{

    /** TableName=JP_PivotWindowDefaultField */
    public static final String Table_Name = "JP_PivotWindowDefaultField";

    /** AD_Table_ID=1000151 */
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

    /** Column name JP_PivotFieldPosition */
    public static final String COLUMNNAME_JP_PivotFieldPosition = "JP_PivotFieldPosition";

	/** Set Predefined Field position 	  */
	public void setJP_PivotFieldPosition (String JP_PivotFieldPosition);

	/** Get Predefined Field position 	  */
	public String getJP_PivotFieldPosition();

    /** Column name JP_PivotWindowDefaultField_ID */
    public static final String COLUMNNAME_JP_PivotWindowDefaultField_ID = "JP_PivotWindowDefaultField_ID";

	/** Set JP_PivotWindowDefaultField	  */
	public void setJP_PivotWindowDefaultField_ID (int JP_PivotWindowDefaultField_ID);

	/** Get JP_PivotWindowDefaultField	  */
	public int getJP_PivotWindowDefaultField_ID();

    /** Column name JP_PivotWindowDefaultField_UU */
    public static final String COLUMNNAME_JP_PivotWindowDefaultField_UU = "JP_PivotWindowDefaultField_UU";

	/** Set JP_PivotWindowDefaultField_UU	  */
	public void setJP_PivotWindowDefaultField_UU (String JP_PivotWindowDefaultField_UU);

	/** Get JP_PivotWindowDefaultField_UU	  */
	public String getJP_PivotWindowDefaultField_UU();

    /** Column name JP_PivotWindowDefault_ID */
    public static final String COLUMNNAME_JP_PivotWindowDefault_ID = "JP_PivotWindowDefault_ID";

	/** Set JP_PivotWindowDefault	  */
	public void setJP_PivotWindowDefault_ID (int JP_PivotWindowDefault_ID);

	/** Get JP_PivotWindowDefault	  */
	public int getJP_PivotWindowDefault_ID();

	public I_JP_PivotWindowDefault getJP_PivotWindowDefault() throws RuntimeException;

    /** Column name JP_PivotWindowField_ID */
    public static final String COLUMNNAME_JP_PivotWindowField_ID = "JP_PivotWindowField_ID";

	/** Set JP_PivotWindowField	  */
	public void setJP_PivotWindowField_ID (int JP_PivotWindowField_ID);

	/** Get JP_PivotWindowField	  */
	public int getJP_PivotWindowField_ID();

	public I_JP_PivotWindowField getJP_PivotWindowField() throws RuntimeException;

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
