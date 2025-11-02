package domain;

import java.util.List;
import java.util.Iterator;

public class ExtendedIteratorKlasea implements ExtendedIterator{
	private List<String> departingCities;
	private int ind=-1;
	public ExtendedIteratorKlasea(List<String> departingCities) {
		this.departingCities=departingCities;
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return ind+1 < departingCities.size();
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		ind++;
		return departingCities.get(ind);
	}

	@Override
	public Object previous() {
		// TODO Auto-generated method stub
		ind--;
		return departingCities.get(ind);
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return ind-1 >= 0;
	}

	@Override
	public void goFirst() {
		// TODO Auto-generated method stub
		ind=-1;
	}

	@Override
	public void goLast() {
		// TODO Auto-generated method stub
		ind=departingCities.size();
	}
	
}
