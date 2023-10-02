package med.voll.api.infra.errores;

// extends Throwable si queremos tratar el error desde la capa mas externa
// extends RuntimeException si queremos tratar el error una vez se detiene la ejecucion
public class ValidacionDeIntegridad extends RuntimeException {
    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
