package com.buddhapants.modal;

public class ProductsModal {
	private String body_html;
	private String created_at;
	private String handle;
	private String id;
	private String product_type;
	private String published_at;
	private String published_scope;
	private String template_suffix;
	private String title;
	private String updated_at;
	private String vendor;
	private String tags;
	private VariantModal variants;
	private OptionsModal options;
	private ImagesModal images;

	public String getBody_html() {
		return body_html;
	}

	public void setBody_html(String body_html) {
		this.body_html = body_html;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getPublished_at() {
		return published_at;
	}

	public void setPublished_at(String published_at) {
		this.published_at = published_at;
	}

	public String getPublished_scope() {
		return published_scope;
	}

	public void setPublished_scope(String published_scope) {
		this.published_scope = published_scope;
	}

	public String getTemplate_suffix() {
		return template_suffix;
	}

	public void setTemplate_suffix(String template_suffix) {
		this.template_suffix = template_suffix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public VariantModal getVariants() {
		return variants;
	}

	public void setVariants(VariantModal variants) {
		this.variants = variants;
	}

	public OptionsModal getOptions() {
		return options;
	}

	public void setOptions(OptionsModal options) {
		this.options = options;
	}

	public ImagesModal getImages() {
		return images;
	}

	public void setImages(ImagesModal images) {
		this.images = images;
	}

}
