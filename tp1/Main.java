package tp1;
public class Main {
	private static boolean mutable = false;
	private static boolean efficace = false;
	private static boolean fraction = false;
	public static void main(String[] args){
		if(args[0].equals("m")) {
			System.out.println("mutable");
			mutable = true;
		}else{
			System.out.println("immutable");
			mutable = false;
		}
		if(args.length==3){
			if(args[1].equals("e")) {
				System.out.println("efficace");
				efficace = true;
			}else{
				System.out.println("symbolique");
				efficace = false;
			}
			if(args[2].equals("f")) {
				System.out.println("fraction");
				fraction = true;
			}else{
				System.out.println("quotient");
				fraction = false;
			}
			if(mutable && efficace && fraction){
				System.out.println("option non reconnue.");
				System.exit(1);
			}
			if(mutable && !efficace && !fraction){
				System.out.println("option non reconnue.");
				System.exit(1);
			}
		}
		FabriqueNat fabN = mutable ? FabriquerNat.MUTABLE : FabriquerNat.IMMUTABLE;
		System.out.println("**** Test de " + fabN.getClass() + "  ****");
		
		test(fabN);
		FabriqueZ fabZ = mutable ? FabriquerZ.MUTABLE : FabriquerZ.IMMUTABLE;
		System.out.println("**** Test de " + fabZ.getClass() + "  ****");
		test(fabN, fabZ);

		FabriqueReel fabR = mutable ? FabriquerReel.MUTABLE : FabriquerReel.IMMUTABLE;

		FabriqueRationnel fabRat = fraction ? FabriqueFraction.getInstance() : FabriqueQuotient.getInstance();
		if(!mutable){
			QEfficace.setFabriqueRationnel(fabRat);
			QEfficace.setFabriqueReel(fabR);
			QEfficace.setFabriqueZ(fabZ);
			QSymbolique.setFabriqueRationnel(fabRat);
			QSymbolique.setFabriqueReel(fabR);
			QSymbolique.setFabriqueZ(fabZ);
		}else{
			if(efficace){
				QMutableEfficace.setFabriqueRationnel(fabRat);
				QMutableEfficace.setFabriqueReel(fabR);
				QMutableEfficace.setFabriqueZ(fabZ);
			}else{
				QMutableSymbolique.setFabriqueRationnel(fabRat);
				QMutableSymbolique.setFabriqueReel(fabR);
				QMutableSymbolique.setFabriqueZ(fabZ);
			}
		}
		FabriqueQ fabQ = efficace ? 
				(mutable ? FabriquerQ.MUTABLE_EFFICACE : FabriquerQ.EFFICACE) :
					(mutable ? FabriquerQ.MUTABLE_SYMBOLIQUE : FabriquerQ.SYMBOLIQUE) ;

				System.out.println("**** Test de " + fabQ.getClass()  + " et de " + fabRat.getClass() + "  ****"); 
				test(fabZ, fabR, fabQ);
	}
	
	private static void test(FabriqueNat fabrique){
		Nat x = null;
		Nat zero = fabrique.creer();
		System.out.println("0 ? " + zero);
		x = fabrique.creer();
		//ce test est inutile. Il n'y a vraiment aucune raison pour que
		//zero equal x.zero à moins d'utiliser des singletons
		System.out.println("true ? " + zero.equals(x.zero()));
		Nat un = fabrique.creer(zero);
		System.out.println("1 ? " + un);
		x = fabrique.creer();
		//ce test est inutile. Il n'y a vraiment aucune raison pour que
		//un equal x.un à moins d'utiliser des singletons
		System.out.println("true ? " + un.equals(x.un()));
		Nat cinq = fabrique.creer(5);
		System.out.println("5 ? " + cinq);
		x = fabrique.creer(cinq);
		System.out.println("6 ? " + x);
		x = x.somme(cinq);
		System.out.println("11 ? " + x);
		x = x.produit(cinq);
		System.out.println("55 ? " + x);
		x = x.zero();
		for(int i = 0; i < 100000000; i++){
			x = x.somme(cinq);
		}
		System.out.println((5 * 100000000) + " ? " + x);
		if(mutable){
			System.out.println("Cast to immutable");
			System.out.println(FabriquerNat.immutable(x));
		}else{
			System.out.println("Cast to mutable");
			System.out.println(FabriquerNat.mutable(x));
		}
	}
	private static void test(FabriqueNat fabN, FabriqueZ fabrique){
		Z x = null;
		Z zero = fabrique.creer(0);
		System.out.println("0 | 0 - 0 ? " + zero);
		x = fabrique.creer(2);
		System.out.println("0 = 0 ? " + zero.equals(x.zero()));
		zero = fabrique.creer(true, fabN.creer());
		System.out.println("0 | 0 - 0 ? " + zero);
		System.out.println("0 = 0 ? " + zero.equals(x.zero()));
		zero = fabrique.creer(fabN.creer(), fabN.creer());
		System.out.println("0 | 0 - 0 ? " + zero);
		System.out.println("0 = 0 ? " + zero.equals(x.zero()));

		Z un = fabrique.creer(1);
		System.out.println("1 | 1 - 0 ? " + un);
		System.out.println("1 = 1 ? " + un.equals(x.un()));
		un = fabrique.creer(true, fabN.creer(1));
		System.out.println("1 | 1 - 0 ? " + un);
		System.out.println("1 = 1 ? " + un.equals(x.un()));
		un = fabrique.creer(fabN.creer(1), fabN.creer());
		System.out.println("1 | 1 - 0 ? " + un);
		System.out.println("1 = 1 ? " + un.equals(x.un()));

		Z moinsUn = fabrique.creer(- 1);
		System.out.println("-1 | 0 - 1 ? " + moinsUn);
		System.out.println("-1 = -1 ? " + moinsUn.equals(x.un().oppose()));
		moinsUn = fabrique.creer(false, fabN.creer(1));
		System.out.println("-1 | 0 - 1 ? " + moinsUn);
		System.out.println("-1 = -1 ? " + moinsUn.equals(x.un().oppose()));
		System.out.println("-1 | 0 - 1 ? " + moinsUn);
		System.out.println("-1 = -1 ? " + moinsUn.equals(x.un().oppose()));
		
		Z moinsCinq = fabrique.creer(- 5);
		System.out.println("-5 | 0 - 5 ? " + moinsCinq);
		Z six = fabrique.creer(6);
		System.out.println("6 | 6 - 0 ? " + six);
		x = x.zero();
		x = x.somme(moinsCinq).somme(six);
		System.out.println("1 | 6 - 5 ? " + x);
		System.out.println("1 | 61 - 60 ? " + x.produit(x));
		x = x.un();
		x = x.produit(moinsCinq).produit(six);
		System.out.println("-30 | 0 - 30 ? " + x);
		System.out.println("-30 <= 0 ? " + x.estNegatif());
		System.out.println("-30 >= 0 ? " + x.estPositif());
		System.out.println("0 - 30 ? " + x.diminuende() + " - " + x.diminuteur());
		x = x.zero();
		for(int i = 0; i < 100000000; i++){
			x = x.somme(moinsUn);
		}
		System.out.println((-1 * 100000000) + " ? " + x);
		if(mutable){
			System.out.println("Cast to immutable");
			System.out.println(FabriquerZ.immutable(x));
		}else{
			System.out.println("Cast to mutable");
			System.out.println(FabriquerZ.mutable(x));
		}
	}

	private static void test(FabriqueZ fabZ, FabriqueReel fabR, FabriqueQ fabQ){
		Q x = null;
		Z moinsUn = fabZ.creer(-1);
		Z moinsDeux = fabZ.creer(-2);
		Q unDemi = fabQ.creer(moinsUn, moinsDeux);
		System.out.println("-1/-2 ou 0.5 ? " + unDemi);
		Q deuxQ = fabQ.creer(moinsDeux, moinsUn);
		System.out.println("-2/-1 ou 2 ? " + deuxQ);
		x = fabQ.creer(deuxQ.quotient());
		x = x.inverse();
		System.out.println("1/2 ou 0.5 ? "+ x);
		System.out.println("1/2 = 1/2 ? " + unDemi.equals(x));
		x = x.somme(unDemi);
		System.out.println("1 ? " + x);
		x = x.somme(fabQ.creer(fabR.creer(-0.5)));
		x = x.produit(unDemi);
		System.out.println("1/4 ou 0.25 ? " + x);
		x = fabQ.creer(fabR.creer(0));
		for(int i = 0; i < 100000000; i++){
			x = x.somme(deuxQ);
		}
		System.out.println((2 * 100000000) + " ? " + x);
		if(x instanceof Mutable){
			System.out.println("Cast to immutable");
			System.out.println(FabriquerQ.immutable(x));
		}else{
			System.out.println("Cast to mutable");
			System.out.println(FabriquerQ.mutable(x));
		}

	}
}