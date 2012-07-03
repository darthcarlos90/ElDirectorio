package directorio.objetos;

public class Imagen {
	private int id;
	private int galeriaId;
	private String descripcion;
	private String imageUrl;
	private String ThumbUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGaleriaId() {
		return galeriaId;
	}

	public void setGaleriaId(int galeriaId) {
		this.galeriaId = galeriaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbUrl() {
		return ThumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		ThumbUrl = thumbUrl;
	}
}
