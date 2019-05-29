/**
 * @author Rivera Garcia Ignacio
 * @author Garcia Zarraga Angelica Lizbeth
 * @date 
 * Implementacion de una Tabla de Dispersion.
 */
package practica9;
/**
 * Interfaz funcional para implementar funciones de dispersión con lambdas.
 */
@FunctionalInterface
public interface Dispersor<K>{

    int dispersa(K elemento);    
}
