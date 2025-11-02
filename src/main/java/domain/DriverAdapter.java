package domain;

import java.util.Date;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class DriverAdapter implements TableModel {
	private Driver d;
	public String[] columnNames = new String[5];
	
	
	public DriverAdapter(Driver d) {
		this.d=d;
		columnNames[0]= "from";
		columnNames[1]= "to";
		columnNames[2]= "date";
		columnNames[3]= "places";
		columnNames[4]= "prices";
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return d.getRides().size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex<0 || columnIndex>4) {
			return "Null";
		}else {
			return columnNames[columnIndex];
		}
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex<=1) {
			return String.class;
		}else if(columnIndex==2){
			return Date.class;
		}else if(columnIndex==3){
			return Integer.class;
		}else if(columnIndex==4){
			return Float.class;
		}else {
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
		if(rowIndex<this.getRowCount() && rowIndex>=0) {
			
			Ride r=d.getRides().get(rowIndex);
			if(columnIndex == 0) return r.getFrom();
			if(columnIndex == 1) return r.getTo();
			if(columnIndex == 2) return r.getDate();
			if(columnIndex == 3) return r.getnPlaces();
			if(columnIndex == 4) return r.getPrice();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
}
