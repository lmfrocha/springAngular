package com.algaworks.alamoneyapi.model;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

		private String logradouro;
		private String numero;
		private String complemento;
		private String bairro;
		private String cep;
		private String cidade;
		private String estado;
		/**
		 * @return the logradouro
		 */
		public String getLogradouro() {
			return logradouro;
		}
		/**
		 * @param logradouro the logradouro to set
		 */
		public void setLogradouro(String logradouro) {
			this.logradouro = logradouro;
		}
		/**
		 * @return the numero
		 */
		public String getNumero() {
			return numero;
		}
		/**
		 * @param numero the numero to set
		 */
		public void setNumero(String numero) {
			this.numero = numero;
		}
		/**
		 * @return the complemento
		 */
		public String getComplemento() {
			return complemento;
		}
		/**
		 * @param complemento the complemento to set
		 */
		public void setComplemento(String complemento) {
			this.complemento = complemento;
		}
		/**
		 * @return the bairro
		 */
		public String getBairro() {
			return bairro;
		}
		/**
		 * @param bairro the bairro to set
		 */
		public void setBairro(String bairro) {
			this.bairro = bairro;
		}
		/**
		 * @return the cep
		 */
		public String getCep() {
			return cep;
		}
		/**
		 * @param cep the cep to set
		 */
		public void setCep(String cep) {
			this.cep = cep;
		}
		/**
		 * @return the cidade
		 */
		public String getCidade() {
			return cidade;
		}
		/**
		 * @param cidade the cidade to set
		 */
		public void setCidade(String cidade) {
			this.cidade = cidade;
		}
		/**
		 * @return the estado
		 */
		public String getEstado() {
			return estado;
		}
		/**
		 * @param estado the estado to set
		 */
		public void setEstado(String estado) {
			this.estado = estado;
		}
		
		
		
}
