package jpiere.plugin.pivotwindow.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MPivotWindowDefaultField extends X_JP_PivotWindowDefaultField {
	
	public MPivotWindowDefaultField(Properties ctx, int JP_PivotWindowDefaultField_ID, String trxName)
	{
		super(ctx, JP_PivotWindowDefaultField_ID, trxName);
	}
	
	public MPivotWindowDefaultField(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
}
