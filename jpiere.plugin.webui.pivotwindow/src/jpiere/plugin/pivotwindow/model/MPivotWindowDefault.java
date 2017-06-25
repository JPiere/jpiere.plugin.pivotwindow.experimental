package jpiere.plugin.pivotwindow.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MTable;
import org.compiere.model.Query;

public class MPivotWindowDefault extends X_JP_PivotWindowDefault {
	
	private MPivotWindowDefaultField[] m_pivotDefaultFields = null;
	
	public MPivotWindowDefault(Properties ctx, int JP_PivotWindowDefault_ID, String trxName) 
	{
		super(ctx, JP_PivotWindowDefault_ID, trxName);
	}
	
	public MPivotWindowDefault(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	public MPivotWindowDefaultField[] getPivotWindowDefaultFields() 
	{
		if(m_pivotDefaultFields != null)
			return m_pivotDefaultFields;
		
		Query query = new Query(getCtx(), MTable.get(getCtx(), I_JP_PivotWindowDefaultField.Table_ID), I_JP_PivotWindowDefaultField.COLUMNNAME_JP_PivotWindowDefault_ID+"=?", get_TrxName());
		List<MPivotWindowDefaultField> list = query.setParameters(getJP_PivotWindowDefault_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("SeqNo, JP_PivotWindowDefaultField_ID")
				.list();
		
		m_pivotDefaultFields = list.toArray(new MPivotWindowDefaultField[0]);
		return m_pivotDefaultFields;
	}
	
}
