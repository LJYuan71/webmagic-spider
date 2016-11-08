package test;

import java.util.ArrayList;
import java.util.List;

import org.seebug.pojo.Seebug;
import org.seebug.service.impl.SeebugServiceImpl;

public class TestAdd {

	public static void main(String[] args) {
		Seebug seebug = new Seebug();
		seebug.setBugId("1");
		Seebug seebug2 = new Seebug();
		seebug.setBugId("2");
		Seebug seebug3 = new Seebug();
		seebug.setBugId("3");
		Seebug seebug4 = new Seebug();
		seebug.setBugId("4");
		Seebug seebug5 = new Seebug();
		seebug.setBugId("5");
		List<Seebug> seebugs = new ArrayList<Seebug>();
		seebugs.add(seebug);
		seebugs.add(seebug2);
		seebugs.add(seebug3);
		seebugs.add(seebug4);
		seebugs.add(seebug5);
		new SeebugServiceImpl().addSeebugBatch(seebugs);
		
	}

}
