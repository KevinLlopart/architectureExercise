package tp1;
import java.util.*;

class Messages {
	public static final String SANS_PREDECESSEUR = 
			"Cet entier naturel vaut zéro : il n'a pas de prédécesseur."; 
	public static final String INT_NON_NATUREL = 
			" est un entier strictement négatif. Il devrait être positif ou nul.";
	public static final String NAT_MUTABLE = 
			" est un entier naturel mutable. Il devrait être immutable.";
	public static final String NAT_NON_MUTABLE = 
			" n'est pas un entier naturel mutable. Il devrait être mutable.";
	public static final String Z_MUTABLE = 
			" est un entier relatif mutable. Il devrait être immutable.";
	public static final String Z_NON_MUTABLE = 
			" n'est pas un entier relatif mutable. Il devrait être mutable.";
	public static final String FABNAT_MUTABLE = 
			" est une fabrique d'entiers naturels mutables. Ils devraient être immutables.";
	public static final String FABNAT_IMMUTABLE = 
			" est une fabrique d'entiers naturels immutables. Ils devraient être mutables.";
	public static final String REEL_MUTABLE = 
			" est un réel mutable. Il devrait être immutable.";
	public static final String REEL_NON_MUTABLE = 
			" n'est pas un réel mutable. Il devrait être mutable.";
	public static final String RATIONNEL_MUTABLE = 
			" est un rationnel mutable. Il devrait être immutable.";
	public static final String RATIONNEL_NON_MUTABLE = 
			" n'est pas un rationnel mutable. Il devrait être mutable.";
	public static final String DOUBLE_SANS_PARTIE_ENTIERE = " n'a pas de partie entière calculable par un int.";
	public static final String FABREEL_MUTABLE = 
			" est une fabrique de réels mutables. Ils devraient être immutables.";
	public static final String FABREEL_IMMUTABLE = 
			" est une fabrique de réels immutables. Ils devraient être mutables.";
	public static final String FABZ_MUTABLE = 
			" est une fabrique d'entiers relatifs mutables. Ils devraient être immutables.";
	public static final String FABZ_IMMUTABLE = 
			" est une fabrique d'entiers relatifs immutables. Ils devraient être mutables.";
	public static final String FABRAT_INEFFICACE = 
			" est une fabrique de rationnels symboliques (implémentés par des fractions). Ils devraient être implémentés par des quotients.";
	public static final String FABRAT_EFFICACE = 
			" est une fabrique de rationnels efficaces (implémentés par des quotients). Ils devraient être implémentés par des fractions.";
}

interface SemiAnneauNat {
	Nat somme(Nat x);
	Nat zero();
	Nat produit(Nat x);
	Nat un();
}

interface NombreNaturel {
	int val(); // Convertit l'entier naturel en int   
	boolean estNul(); // Teste à zéro l'entier naturel
	NombreNaturel predecesseur(); // Donne le prédécesseur s'il existe
}

interface FabriqueNat {
	Nat creer(int val); // Crée un entier valant val (supposé positif) 
	Nat creer(); // Crée un entier valant zéro 
	Nat creer(Nat predecesseur); // Crée un entier naturel égal au successeur de pred
}

interface Nat extends NombreNaturel, FabriqueNat, SemiAnneauNat {
	Nat predecesseur(); // Donne le prédécesseur s'il existe (spécialisation)
	boolean equals(Object o); // Renvoie false
	//   si o n'est pas de type Nat,
	// teste l'égalité des entiers naturels sinon
	String toString(); // Affiche l'entier renvoyé par val()
}

// - bottom up - bas - immutable - classe IntPositif
//DONE!

/**
 * @author noomi
 *Pour rendre IntPositif immutable, on doit soit ajouter le modifier final à la classe (immutabilité forte) 
 *soit ajouter ce même modifier à toutes les méthodes (immutabilité faible). 
 *La première option n'est pas envisageable puisqu'il faut étendre IntPositif dans NatParIntPositif.
 Or NatParIntPositif implémente par ailleurs Nat, qui sensée spécifier la méthode  predecesseur de IntPositif.
 Ce n'est pas permis si cette méthode est immutable dans IntPositif. Afin de respecter l'obligation de conserver IntPositif en immutable, il faut la rendre abstraite.
 */
abstract class IntPositif implements NombreNaturel{
	protected int i;

	public final int val() {
		return i;
	}

	public final boolean estNul() {
		return i==0;
	}
}
// bottom up - haut - immutable - classe NatParIntPositif
//DONE!
final class NatParIntPositif extends IntPositif implements Nat{

	public NatParIntPositif(int j) {
		this.i=j;
	}

	public Nat creer(int val) {
		return new NatParIntPositif(val);
	}

	public Nat creer() {
		return new NatParIntPositif(0);
	}

	public Nat creer(Nat predecesseur) {
		return new NatParIntPositif(predecesseur.val()+1);
	}

	public Nat somme(Nat x) {
		return new NatParIntPositif(val()+x.val());
	}

	public Nat zero() {
		return new NatParIntPositif(0);
	}

	public Nat produit(Nat x) {
		return new NatParIntPositif(val()*x.val());
	}

	public Nat un() {
		return new NatParIntPositif(1);
	}
	public Nat predecesseur(){
		if(estNul()){
			return creer();
		}
		else{
			return creer(val()-1);
		}
	}

}
/*
 * Interface utilisée pour étiqueter les données mutables (ou les fabriques de données mutables).
 */
interface Mutable {}

// bottom up - haut - mutable - classe NatMutableParIntPositif
//DONE!
class NatMutableParIntPositif extends IntPositif implements Nat,Mutable{
	
	public NatMutableParIntPositif(int j) {
		this.i=j;
	}
	public Nat creer(int val) {
		return new NatMutableParIntPositif(val);
	}

	public Nat creer() {
		return new NatMutableParIntPositif(0);
	}

	public Nat creer(Nat predecesseur) {
		return new NatMutableParIntPositif(predecesseur.val()+1);
	}

	public Nat somme(Nat x) {
		this.i+=x.val();
		return this;
	}

	public Nat zero() {
		return new NatMutableParIntPositif(0);
	}

	public Nat produit(Nat x) {
		this.i*=x.val();
		return this;
	}

	public Nat un() {
		return new NatMutableParIntPositif(1);
	}

	public Nat predecesseur(){
		if(estNul()){
			return creer();
		}
		else{
			return creer(val()-1);
		}
	}
	
}
//classe FabriquerNat
//DONE!
class FabriquerNat {
	public static final FabriqueNat MUTABLE = new NatMutableParIntPositif(0);
	public static final FabriqueNat IMMUTABLE = new NatParIntPositif(0);
	public static Nat mutable(Nat n){
		if(!(n instanceof Mutable)){
			return MUTABLE.creer(n.val());
		}else{
			throw new IllegalArgumentException(n + Messages.NAT_MUTABLE);
		}
	}
	public static Nat immutable(Nat n){
		if(n instanceof Mutable){
			return IMMUTABLE.creer(n.val());
		}else{
			throw new IllegalArgumentException(n + Messages.NAT_NON_MUTABLE);
		}
	}
}

interface Relatif {
	int val(); // Convertit en un int
	boolean estPositif(); // Teste si l'entier relatif est positif
	boolean estNegatif(); // Teste si l'entier relatif est négatif
	Nat valAbsolue(); // Renvoie la valeur absolue de l'entier relatif
	Nat diminuende(); // Renvoie le diminuende associé à l'entier relatif 
	Nat diminuteur(); // Renvoie le diminuteur associé à l'entier relatif 
}

interface FabriqueZ {
	Z creer(boolean signe, Nat abs); // Crée un entier relatif de signe et de valeur absolue donnés
	Z creer(Nat diminuende, Nat diminuteur); // Crée un entier relatif correspondant 
	//   à la différence diminuende - diminuteur  
	Z creer(int val); // Crée un entier relatif valant val
}

interface AnneauZ {
	Z somme(Z a);
	Z zero();
	Z oppose();
	Z produit(Z a);
	Z un();
}

interface Z extends Relatif, FabriqueZ, AnneauZ {
	boolean equals(Object o); // Renvoie false
	//   si o n'est pas de type Z,
	// teste l'égalité des entiers relatifs sinon
	String toString(); // Représente l'entier relatif sous la forme d'un int
}


// Interface utilisée pour étiqueter les données réalisant des calculs de manière efficace.
// (efficace = utilisant directement un type Java sous-jacent)

interface Efficace {}

//top down - haut - immutable - classe ZEfficace
//DONE!
abstract class ZEfficace implements Z, Efficace{

    @Override
    public final Z somme(Z a) {
        return this.creer(a.val() + this.val());
    }

    @Override
    public final Z zero() {
        return this.creer(0);
    }

    @Override
    public final Z oppose() {
        return this.creer(-this.val());
    }

    @Override
    public final Z produit(Z a) {
        return this.creer(this.val()*a.val());
    }

    @Override
    public final Z un() {
        return this.creer(1);
    }
}

//top down - bas - immutable - classe ZEfficaceParInt
//DONE!
final class ZEfficaceParInt extends ZEfficace implements Z{

    protected int val;

    public ZEfficaceParInt(int val) {
        this.val = val;
    }

    // Dépendance relative à une fabrique d'entiers naturels
    private static FabriqueNat fabNat = FabriquerNat.IMMUTABLE;

    public static void setFabriqueNat(FabriqueNat fabNat){
        if(fabNat instanceof Mutable){
            throw new IllegalArgumentException(fabNat + Messages.FABNAT_MUTABLE);
        }
        ZEfficaceParInt.fabNat = fabNat;
    }

    @Override
    public Z creer(boolean signe, Nat abs) {
        return signe ? new ZEfficaceParInt(abs.val()) : new ZEfficaceParInt((-abs.val()));
    }

    @Override
    public Z creer(Nat diminuende, Nat diminuteur) {
        return new ZEfficaceParInt(diminuende.val() - diminuteur.val());
    }

    @Override
    public Z creer(int val) {
        return new ZEfficaceParInt(val);
    }

    @Override
    public int val() {
        return val;
    }

    @Override
    public boolean estPositif() {
        return val >= 0;
    }

    @Override
    public boolean estNegatif() {
        return val <= 0;
    }

    @Override
    public Nat valAbsolue() {
        return val >= 0 ? fabNat.creer(val) : fabNat.creer(-val);
    }

    @Override
    public Nat diminuende() {
        return val>=0 ? fabNat.creer(val) : fabNat.creer(0);
    }

    @Override
    public Nat diminuteur() {
    	return val>=0 ? fabNat.creer(0) : fabNat.creer(-val);
    }
}

// top down - haut - mutable - classe ZEfficaceMutable
//DONE!
abstract class ZEfficaceMutable implements Z, Efficace,Mutable{

  @Override
  public Z zero() {
      return this.creer(0);
  }

  @Override
  public Z oppose() {
      return this.creer(-this.val());
  }
  protected int val;
  @Override
  public Z un() {
      return this.creer(1);
  }
}
// top down - bas - mutable - class ZEfficaceMutableParInt
//DONE!
class ZEfficaceMutableParInt extends ZEfficaceMutable implements Z{

    protected int val;
    // Dépendance relative à une fabrique d'entiers naturels
    private static FabriqueNat fabNat = FabriquerNat.MUTABLE;

    public ZEfficaceMutableParInt(int val) {
        this.val = val;
    }
    @Override
    public Z somme(Z a) {
  	  this.val+=a.val();
        return this;
    }
    @Override
    public Z produit(Z a) {
  	  this.val*=a.val();
        return this;
    }

    public static void setFabriqueNat(FabriqueNat fabNat){
        if(! (fabNat instanceof Mutable)){
            throw new IllegalArgumentException(fabNat + Messages.FABNAT_IMMUTABLE);
        }
        ZEfficaceMutableParInt.fabNat = fabNat;
    }

    @Override
    public Z creer(boolean signe, Nat abs) {
        return signe ? new ZEfficaceMutableParInt(abs.val()) : new ZEfficaceMutableParInt((-abs.val()));
    }

    @Override
    public Z creer(Nat diminuende, Nat diminuteur) {
        return new ZEfficaceMutableParInt(diminuende.val() - diminuteur.val());
    }

    @Override
    public Z creer(int val) {
        return new ZEfficaceMutableParInt(val);
    }

    @Override
    public int val() {
        return val;
    }

    @Override
    public boolean estPositif() {
        return val >= 0;
    }

    @Override
    public boolean estNegatif() {
        return val <= 0;
    }

    @Override
    public Nat valAbsolue() {
        return val >= 0 ? fabNat.creer(val) : fabNat.creer(-val);
    }

    @Override
    public Nat diminuende() {
        return val>=0 ? fabNat.creer(val) : fabNat.creer(0);
    }

    @Override
    public Nat diminuteur() {
    	return val>=0 ? fabNat.creer(0) : fabNat.creer(-val);
    }
}

// classe FabriquerZ
//DONE!
class FabriquerZ {
	public static final FabriqueZ IMMUTABLE = new ZEfficaceParInt(0); 
	public static final FabriqueZ MUTABLE = new ZEfficaceMutableParInt(0);
	public static Z mutable(Z n){
		if(!(n instanceof Mutable)){
			return MUTABLE.creer(n.val());
		}else{
			throw new IllegalArgumentException(n + Messages.Z_MUTABLE);
		}
	}
	public static Z immutable(Z n){
		if(n instanceof Mutable){
			return IMMUTABLE.creer(n.val());
		}else{
			throw new IllegalArgumentException(n + Messages.Z_NON_MUTABLE);
		}
	}
}
/*
interface FabriqueReel {
	Reel creer(double r);
}

interface CorpsReel {
	Reel somme(Reel a);
	Reel zero();
	Reel oppose();
	Reel produit(Reel a);
	Reel un();
	Reel inverse();
}

interface Reel extends FabriqueReel, CorpsReel {
	double val();
}
// TODO - Agrégation simple - immutable - classe ReelParDouble
class ReelParDouble implements Reel {
	private static double PRECISION = 1e-12; // Précision relative lors des tests d'égalité.
}

// TODO - Agrégation simple - mutable - classe ReelMutableParDouble


// TODO (compléter) - classe FabriquerReel
class FabriquerReel {

}

interface Rationnel {
	Z numerateur(); // Renvoie le numérateur
	Z denominateur(); // Renvoie le dénominateur
	Reel quotient(); // Renvoie le quotient
}

interface FabriqueQ {
	Q creer(Z numerateur, Z denominateur); // Crée le rationnel "numerateur"/"denominateur"
	Q creer(Reel rationnel); // // Crée le rationnel de valeur réelle "rationnel"
}

interface CorpsQ {
	Q somme(Q a);
	Q zero();
	Q oppose();
	Q produit(Q a);
	Q un();
	Q inverse();
}

interface Q extends Rationnel, FabriqueQ, CorpsQ {
	boolean equals(Object o); // Renvoie false
	//   si o n'est pas de type Q,
	// teste l'égalité des rationnels sinon
	String toString(); // Représente le rationnel sous la forme "numerateur/denominateur"
}


// TODO - agrégation avec délégation - bas - classe RationnelParQuotient
class RationnelParQuotient implements Rationnel, Efficace {
	private FabriqueReel fabR; //  fabrique de réels
	private FabriqueZ fabZ; // fabrique d'entiers relatifs


}

// TODO - agrégation avec délégation - bas - classe RationnelParFraction
class RationnelParFraction implements Rationnel {
	private FabriqueReel fabR; //  fabrique de réels
	private FabriqueZ fabZ; // fabrique d'entiers relatifs

}

interface FabriqueRationnel {
	Rationnel creer(Z numerateur, Z denominateur, FabriqueReel fabR, FabriqueZ fabZ);
	Rationnel creer(Reel rationnel, FabriqueReel fabR, FabriqueZ fabZ);
}

// TODO - implémentation de fabriques par des classes singletons FabriqueFraction et  FabriqueQuotient


// TODO (compléter) agrégation avec délégation - haut - classe abstraite QAbstrait (factorisation de la délégation)
abstract class QAbstrait implements Q {
	abstract public Q creer(Z numerateur, Z denominateur);
	abstract public Q creer(Reel rationnel);
	abstract public Q somme(Q a);
	abstract public Q zero();
	abstract public Q oppose();
	abstract public Q produit(Q a);
	abstract public Q un();
	abstract public Q inverse();
}

// TODO - agrégation avec délégation - haut - immutable, efficace - classe QEfficace
class QEfficace extends QAbstrait implements Q, Efficace {

	private static FabriqueRationnel fabRat = FabriqueQuotient.SINGLETON;
	private static FabriqueReel fabR = FabriquerReel.IMMUTABLE;
	private static FabriqueZ fabZ = FabriquerZ.IMMUTABLE;

	public static void setFabriqueRationnel(FabriqueRationnel fabRat){
		QEfficace.fabRat = fabRat;
	}
	public static void setFabriqueReel(FabriqueReel fabR){
		if(fabR instanceof Mutable){
			throw new IllegalArgumentException(fabR + Messages.FABREEL_MUTABLE);
		}
		QEfficace.fabR = fabR;
	}
	public static void setFabriqueZ(FabriqueZ fabZ){
		if(fabZ instanceof Mutable){
			throw new IllegalArgumentException(fabZ + Messages.FABZ_MUTABLE);
		}
		QEfficace.fabZ = fabZ;
	}

}

// TODO - agrégation avec délégation - haut - immutable, symbolique - classe QSymbolique


// TODO - agrégation avec délégation - haut - mutable, efficace - classe QMutableEfficace


// TODO - agrégation avec délégation - haut - mutable, symbolique - classe QMutableSymbolique


// TODO (compléter) - Fabriques de rationnels
class FabriquerQ {
	public static final FabriqueQ SYMBOLIQUE = null
			public static final FabriqueQ EFFICACE = null;
	public static final FabriqueQ MUTABLE_SYMBOLIQUE = null;
	public static final FabriqueQ MUTABLE_EFFICACE = null;
	public static Q mutable(Q x){
		if(!(x instanceof Mutable)){
			return (x instanceof Efficace) ? 
					MUTABLE_EFFICACE.creer(FabriquerReel.mutable(x.quotient())) :
						MUTABLE_SYMBOLIQUE.creer(FabriquerZ.mutable(x.numerateur()), 
								FabriquerZ.mutable(x.denominateur())) ;
		}else{
			throw new IllegalArgumentException(x + Messages.RATIONNEL_MUTABLE);
		}
	}
	public static Q immutable(Q x){
		if(x instanceof Mutable){
			return (x instanceof Efficace) ? 
					EFFICACE.creer(FabriquerReel.immutable(x.quotient())) :
						SYMBOLIQUE.creer(FabriquerZ.immutable(x.numerateur()), 
								FabriquerZ.immutable(x.denominateur())) ;
		}else{
			throw new IllegalArgumentException(x + Messages.RATIONNEL_NON_MUTABLE);
		}
	}
}
	*/
