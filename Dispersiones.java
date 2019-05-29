/**
 * @author Rivera Garcia Ignacio
 * @author Garcia Zarraga Angelica Lizbeth
 * @date 
 * Implementacion de una Tabla de Dispersion.
 */
package practica9;
/**
 * Clase con varias funciones de dispersión.
 */
public class Dispersiones{
	/*
    public static int dispersionXOR(byte[] a){
    	int hash = 0;

    	for(int i=0;i<a.length;i++){
    		hash = hash ^ a[i];
    	}
    	return hash % getTamano();
    }
    */
    /*
    public static int dispersionBJ(byte[] a){
	// Aquí va su código.
    }
	*/
	public static int dispersionDJB(byte[] a){
		int hash = 0;

		for(int i=0;i<a.length;i++){
			hash = a[i] + ((hash << 5) - hash);
		}
		return hash;
	}
}
