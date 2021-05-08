package JogoGourmet;

public class Repertorio {

    String prato;
    int relacionado;
    int naoEh;
    
    public Repertorio(String p, int r, int n){
        prato = p;
        relacionado = r;
        naoEh = n;
    }

    public void setRelacionado(int r) {
        relacionado = r;
    }
    
    public void setNaoEh(int n) {
        naoEh = n;
    }
    
    public String getPrato() {
        return prato;
    }
    
    public int getRelacionado() {
        return relacionado;
    }
    
    public int getNaoEh() {
        return naoEh;
    }

}