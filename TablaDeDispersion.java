/**
 * @author Rivera Garcia Ignacio
 * @author Garcia Zarraga Angelica Lizbeth
 * @date 
 * Implementacion de una Tabla de Dispersion.
 */
package practica9;
/**
 * Clase que define tablas de dispersión.
 */
public class TablaDeDispersion<K, V>{
	/**
	 * Clase interna Entrada que encapsula un valor con
	 * su llave.
	 */
    private class Entrada{

		public K llave;
		public V valor;
		/**
	 	 * Unico Constructor de Entrada apartir de una
	 	 * llave y una entrada.
	 	 */
		public Entrada(K llave, V valor){
	    	this.llave = llave;
        	this.valor = valor;
		}
    }
 

    private Lista<Entrada>[] tabla;  
    private Dispersor<K> dispersor;
    private int elementos;
    public static final int CAPACIDAD_MINIMA = 64;
    private static final double CARGA_MAXIMA = 0.75;

    /**
	 * metodo auxiliar nuevoArreglo que representa
	 * la tabla de dispersion.
	 * @param int tamano
	 * @return Lista<Entrada>[]
	 */
    private Lista<Entrada>[] nuevoArreglo(int tamano){
	   @SuppressWarnings("unchecked")
	   Lista<Entrada>[] arreglo = (Lista<Entrada>[]) new Lista[tamano];
	   return arreglo;
    }

    //CONSTRUCTORES
    /**
	 * Constructor de una tabla de dispersion vacia
	 * con tamaño predeterminado 128.
	 */
    public TablaDeDispersion(){
	   tabla = nuevoArreglo(128);
       elementos = 0;
       dispersor = (k) -> k.hashCode();
    }
    /**
	 * Constructor que toma el tamaño de la tabla
	 * @param int capacidad.
	 */
    public TablaDeDispersion(int capacidad){
        if(capacidad < CAPACIDAD_MINIMA){
            tabla = nuevoArreglo(128);
        }else{
            int cap = capacidad*2;
            for(int i=0;i<capacidad;i++){
                if(Math.pow(2,i) >= cap){
                    tabla = nuevoArreglo(2^i);
                    break;
                }
                continue;
            }
        }
        elementos = 0;
        dispersor = (k) -> k.hashCode();
    }
    /**
	 * Constructor con un dispersor, iniciando el tamaño
	 * de la tabla en 128.
	 * @param Dispersor<K> dispersor
	 */
    public TablaDeDispersion(Dispersor<K> dispersor){
	    this.dispersor = dispersor;
        tabla = nuevoArreglo(128);
        elementos = 0;
    }
    /**
	 * Constructor de Tabla de dispersion apartir
	 * de un tamaño y un dispersor.
	 * @param int capacidad
	 * @param Dispersor<K> dispersor
	 */
    public TablaDeDispersion(int capacidad, Dispersor<K> dispersor){
	    if(capacidad < CAPACIDAD_MINIMA){
            tabla = nuevoArreglo(128);
        }else{
            int cap = capacidad*2;
            for(int i=0;i<capacidad;i++){
                if(Math.pow(2,i) >= cap){
                    tabla = nuevoArreglo(2^i);
                    break;
                }
                continue;
            }
        }
        elementos = 0;
        this.dispersor = dispersor;
    }

    /**
	 * agrega, agrega un valor a la tabla con su llave, si alguno
	 * es null, cuando la tabla alcanza la CARGA_MAXIMA aumentamos su
	 * tamaño al doble.
	 * @parma K llave, V valor
	 */
    public void agrega(K llave, V valor){
	    if((llave == null) || (valor == null)){
            return;
        }
        Entrada nueva = new Entrada(llave,valor);
        int loc = dispersor.dispersa(llave);

        if(tabla[loc] == null){
            Lista nuevaLista = new Lista();
            nuevaLista.agregaFinal(nueva);
            tabla[loc] = nuevaLista;
        }else{
            for(int i=1;i<tabla[loc].getLongitud();i++){
                if(tabla[loc].get(i).llave == llave){
                    tabla[loc].inserta(i,nueva);
                }
            }
            tabla[loc].agregaFinal(nueva);
        }

        elementos++;

        if((elementos/tabla.length)>CARGA_MAXIMA){
            duplicaTamaño();
        }
    }
 	/**
	 * getValor regresa el valor que esta asociado a una llave
	 * @param K llave
	 * @return V
	 */
    public V getValor(K llave){
	    int loc = dispersor.dispersa(llave);
        if(tabla[loc] == null){
            return null;
        }else{
            for(int i=1;i<tabla[loc].getLongitud();i++){
                Entrada a = tabla[loc].get(i);
                if(a.llave == llave){
                    return a.valor;
                }
            }
            return null;
        }
    }
    /**
	 * contieneLlave nos indica si una K llave esta contenida
	 * en algun lugar de nuestra tabla.
	 * @param K llave
	 * @return boolean
	 */
    public boolean contieneLlave(K llave){
	    int loc = dispersor.dispersa(llave);
        if(tabla[loc] == null){
            return false;
        }else{
            for(int i=1;i<tabla[loc].getLongitud();i++){
                Entrada a = tabla[loc].get(i);
                if(a.llave == llave){
                    return true;
                }
            }
            return false;
        }
    }
    /**
	 * contieneValor nos indica si un valor esta contenido
	 * dentro de nuestra tabla de dispersion.
	 * @param V valor
	 * @return boolean
	 */
    public boolean contieneValor(V valor){
    	for(int i=0;i<tabla.length;i++){
            if(tabla[i] == null){
                continue;
            }else{
                for(int j=1;j<tabla[i].getLongitud();j++){
                	Entrada a = tabla[i].get(j);
                	if(a.valor == valor){
                		return true;
                	}
                	continue;
                }
            }
        }
        return false;
    }
    /**
	 * esVacia nos dice si nuestra tabla de dispersion es vacia.
	 * @return boolean.
	 */
    public boolean esVacia(){
	    return elementos == 0;
    }
    /**
	 * elimina nor regresa el valor asociado a una K llave dada
	 * por el usuario y lo elimina de nuestra tabla.
	 * @param K llave
	 * @return V
	 */
    public V elimina(K llave){
	    int loc = dispersor.dispersa(llave);
        if(tabla[loc] == null){
            return null;
        }else{
            for(int i=1;i<tabla[loc].getLongitud();i++){
                Entrada a = tabla[loc].get(i);
                if(a.llave == llave){
                    V res = a.valor;
                    tabla[loc].elimina(a);
                    return res;
                }
            }
            return null;
        }
    }
    /**
	 * getElementos nos regresa el numero de elementos
	 * que estan dentro de nuestra tabla
	 * @return int.
	 */
    public int getElementos(){
	   return elementos;
    }
    /**
	 * getLlaves nos regresa una lista de las llaves
	 * dentro de nuestra tabla.
	 * @return Lista<K>
	 */
    public Lista<K> getLlaves(){
	    Lista llaves = new Lista();

        for(int i=0;i<tabla.length;i++){
            if(tabla[i] == null){
                continue;
            }else{
                for(int j=1;j<tabla[i].getLongitud();j++){
                	Entrada a = tabla[i].get(j);
                	llaves.agregaFinal(a.llave);
                }
            }
        }
        return llaves;
    }
    /**
	 * getValores nos regresa una lista de los valores
	 * dentro de nuestra tabla.
	 * @return Lista<V>
	 */
    public Lista<V> getValores(){
		Lista valores = new Lista();

        for(int i=0;i<tabla.length;i++){
            if(tabla[i] == null){
                continue;
            }else{
                for(int j=1;j<tabla[i].getLongitud();j++){
                	Entrada a = tabla[i].get(j);
                	valores.agregaFinal(a.valor);
                }
            }
        }
        return valores;
    }
    /**
	 * getTamano nos regresa el tamaño de nuestra tabla
	 * de dispersion
	 * @return int
	 */
    public int getTamano(){
    	return tabla.length;
    }
    /**
	 * duplicaTamaño duplica el tamaño de nuestra tabla
	 * de dispersion al superar la CARGA_MAXIMA
	 */
    public void duplicaTamaño(){
        TablaDeDispersion nTabla = new TablaDeDispersion(tabla.length,dispersor);
        for(int i=0;i<tabla.length;i++){
        	if(tabla[i] == null){
        		continue;
        	}else{
        		for(int j=1;j<tabla[i].getLongitud();j++){
        			Entrada a = tabla[i].get(j);
        			nTabla.agrega(a.llave,a.valor);
        		}
        	}
        }
        tabla = nTabla.tabla;
    }

    public static void main(String[] args){
        TablaDeDispersion tb = new TablaDeDispersion(20);
    }
}
