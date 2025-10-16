package U5_W2_D3.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("La risorsa con id " + id + " non è stata trovata!");
    }
}
