package jpiere.plugin.pivotwindow.form;

import java.text.DecimalFormat;

import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.pivot.Calculator;
import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.PivotHeaderContext;
import org.zkoss.pivot.PivotRendererExt;
import org.zkoss.pivot.Pivottable;

public class JPierePivotRenderer implements PivotRendererExt {
	
	private DecimalFormat _fnf = new DecimalFormat("##,###.00");
	private DecimalFormat _nnf = new DecimalFormat("##,###");
	
	public JPierePivotRenderer() 
	{
		;
	}
	
	public JPierePivotRenderer(String format) 
	{
		if(!Util.isEmpty(format))
			_fnf = new DecimalFormat(format);
	}
	
    
    @Override
    public String renderCell(Number data, Pivottable table,  PivotHeaderContext rowContext, PivotHeaderContext columnContext, PivotField dataField) 
    {
        return data == null ? "" : data instanceof Integer ? _nnf.format(data) : _fnf.format(data);
    }
	     
    @Override
    public int getColumnSize(Pivottable table, PivotHeaderContext colc, PivotField field) 
    {
        return colc.isGrandTotal() && field != null ? 150 : 100;
    }
     
    @Override
    public int getRowSize(Pivottable table, PivotHeaderContext rowc, PivotField field) 
    {
        return 20;
    }
     
    @Override
    public String renderField(Object data, Pivottable table, PivotField field) 
    {
        return field.getType() == PivotField.Type.DATA ? field.getTitle() : data == null ? "(null)" : String.valueOf(data);
    }
     
    String grandTotalText = Msg.getElement(Env.getCtx(), "GrandTotal");
    
    @Override
    public String renderGrandTotalField(Pivottable table, PivotField field) 
    {
        if (field == null) 
        	return grandTotalText;
        
        return grandTotalText + ":" + field.getTitle();
    }
     
    @Override
    public String renderSubtotalField(Object data, Pivottable table, PivotField field, Calculator calculator) 
    {
        String calLabel = calculator.getLabel();
        String dataLabel = data == null ? "Null" : data.toString();
        return dataLabel + " " + calLabel;
    }
     
    @Override
    public String renderDataField(PivotField field) 
    {
        return field.getFieldName();
    }
     
    @Override
    public String renderCellSClass(Number data, Pivottable table, PivotHeaderContext rowContext, PivotHeaderContext columnContext, PivotField dataField) 
    {
        return null;
    }
     
    @Override
    public String renderCellStyle(Number data, Pivottable table, PivotHeaderContext rowContext, PivotHeaderContext columnContext, PivotField dataField) 
    {
        return null;
    }
     
	
}
