/**
 * @author KingZhao
 *         Kylin Soong
 */
package com.jcommerce.core.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "plugins", catalog = "ishop")
public class Plugins extends ModelObject {

	private static final long serialVersionUID = -124141366336721127L;
	private String code;
	private String version;
	private String library;
	private boolean assign;//是否签名
	private Timestamp installDate;

	@Basic( optional = true )
	@Column( length = 30  )
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic( optional = true )
	@Column( length = 10  )
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Basic( optional = true )
	@Column( length = 255  )
	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public boolean isAssign() {
		return assign;
	}

	public void setAssign(boolean assign) {
		this.assign = assign;
	}

	@Basic( optional = true )
	@Column( name = "install_date"  )
	public Timestamp getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Timestamp installDate) {
		this.installDate = installDate;
	}

}
