package com.endeavorms.velocity.qto.config;

/**
 * DTO for passing company configuration properties between the server and the client.
 *
 * @author fcurran
 * @since 2.7.0
 */
public class CompanyConfigPropertiesDto {

    /**
     * ID of the Company that the configuration properties apply to.
     */
    private Long companyId;

    /**
     * Email to use when the import rules fail.
     */
    private String importRuleFailureEmail;

    /**
     * Email to use when the import system fails.
     */
    private String importSystemFailureEmail;

    /**
     * Email from address.
     */
    private String emailFromAddress;

    /**
     * FTP Order import server.
     */
    private String ftpServer;

    /**
     * FTP Order import protocol.
     */
    private String ftpProtocol;

    /**
     * FTP Order import username.
     */
    private String ftpUsername;

    /**
     * FTP Order import password.
     */
    private String ftpPassword;

    /**
     * FTP Order import download directory (remote FTP directory for getting import files).
     */
    private String ftpDownloadDirectory;

    /**
     * FTP Order import upload directory (remote FTP directory for putting files).
     */
    private String ftpUploadDirectory;

    /**
     * FTP Order import download directory (remote FTP directory for getting import files).
     */
    private String ftpDownloadProcessedDirectory;

    /**
     * FTP Order import upload directory (remote FTP directory for putting files).
     */
    private String ftpUploadProcessedDirectory;

    /**
     * URL of the OMS Spreadsheet import API.
     */
    private String importApiUrl;

    /**
     * OMS Spreadsheet import API username.
     */
    private String importApiUser;

    /**
     * OMS Spreadsheet import API password.
     */
    private String importApiPassword;

    /**
     * download to location.
     */
    private String ftpDownloads;

    private String endeavorProxyUrl;

    private Boolean ftdiQuartzEnabled;

    private Boolean ctsQuartzEnabled;

    private Boolean parserQuartzEnabled;

    private Integer disconnectDelay;

    private Boolean clientIdUniqueConstraint;

    private String parserFailureEmailFrom;

    private String parserFailureEmailTo;

    private Boolean autoCreateClientServiceId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getImportRuleFailureEmail() {
        return importRuleFailureEmail;
    }

    public void setImportRuleFailureEmail(final String importRuleFailureEmail) {
        this.importRuleFailureEmail = importRuleFailureEmail;
    }

    public String getImportSystemFailureEmail() {
        return importSystemFailureEmail;
    }

    public void setImportSystemFailureEmail(final String importSystemFailureEmail) {
        this.importSystemFailureEmail = importSystemFailureEmail;
    }

    public String getEmailFromAddress() {
        return emailFromAddress;
    }

    public void setEmailFromAddress(final String emailFromAddress) {
        this.emailFromAddress = emailFromAddress;
    }


    public String getFtpServer() {
        return ftpServer;
    }

    public void setFtpServer(final String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public String getFtpProtocol() {
        return ftpProtocol;
    }

    public void setFtpProtocol(final String ftpProtocol) {
        this.ftpProtocol = ftpProtocol;
    }

    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(final String ftpUsername) {
        this.ftpUsername = ftpUsername;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(final String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpDownloadDirectory() {
        return ftpDownloadDirectory;
    }

    public void setFtpDownloadDirectory(final String ftpDownloadDirectory) {
        this.ftpDownloadDirectory = ftpDownloadDirectory;
    }

    public String getFtpUploadDirectory() {
        return ftpUploadDirectory;
    }

    public void setFtpUploadDirectory(final String ftpUploadDirectory) {
        this.ftpUploadDirectory = ftpUploadDirectory;
    }

    public String getFtpDownloadProcessedDirectory() {
        return ftpDownloadProcessedDirectory;
    }

    public void setFtpDownloadProcessedDirectory(final String ftpDownloadProcessedDirectory) {
        this.ftpDownloadProcessedDirectory = ftpDownloadProcessedDirectory;
    }

    public String getFtpUploadProcessedDirectory() {
        return ftpUploadProcessedDirectory;
    }

    public void setFtpUploadProcessedDirectory(final String ftpUploadProcessedDirectory) {
        this.ftpUploadProcessedDirectory = ftpUploadProcessedDirectory;
    }

    public String getImportApiUrl() {
        return importApiUrl;
    }

    public void setImportApiUrl(final String importApiUrl) {
        this.importApiUrl = importApiUrl;
    }

    public String getImportApiUser() {
        return importApiUser;
    }

    public void setImportApiUser(final String importApiUser) {
        this.importApiUser = importApiUser;
    }

    public String getImportApiPassword() {
        return importApiPassword;
    }

    public void setImportApiPassword(final String importApiPassword) {
        this.importApiPassword = importApiPassword;
    }

    public String getFtpDownloads() {
        return ftpDownloads;
    }

    public void setFtpDownloads(final String ftpDownloads) {
        this.ftpDownloads = ftpDownloads;
    }

    public String getEndeavorProxyUrl() {
        return endeavorProxyUrl;
    }

    public void setEndeavorProxyUrl(final String endeavorProxyUrl) {
        this.endeavorProxyUrl = endeavorProxyUrl;
    }

    public Boolean getFtdiQuartzEnabled() {
        return ftdiQuartzEnabled;
    }

    public void setFtdiQuartzEnabled(final Boolean ftdiQuartzEnabled) {
        this.ftdiQuartzEnabled = ftdiQuartzEnabled;
    }

    public Boolean getCtsQuartzEnabled() {
        return ctsQuartzEnabled;
    }

    public void setCtsQuartzEnabled(final Boolean ctsQuartzEnabled) {
        this.ctsQuartzEnabled = ctsQuartzEnabled;
    }

    public Boolean getParserQuartzEnabled() {
        return parserQuartzEnabled;
    }

    public void setParserQuartzEnabled(final Boolean parserQuartzEnabled) {
        this.parserQuartzEnabled = parserQuartzEnabled;
    }

    public Integer getDisconnectDelay() {
        return disconnectDelay;
    }

    public void setDisconnectDelay(final Integer disconnectDelay) {
        this.disconnectDelay = disconnectDelay;
    }

    public Boolean getClientIdUniqueConstraint() {
        return clientIdUniqueConstraint;
    }

    public void setClientIdUniqueConstraint(final Boolean clientIdUniqueConstraint) {
        this.clientIdUniqueConstraint = clientIdUniqueConstraint;
    }

    public String getParserFailureEmailFrom() {
        return parserFailureEmailFrom;
    }

    public void setParserFailureEmailFrom(final String parserFailureEmailFrom) {
        this.parserFailureEmailFrom = parserFailureEmailFrom;
    }

    public String getParserFailureEmailTo() {
        return parserFailureEmailTo;
    }

    public void setParserFailureEmailTo(final String parserFailureEmailTo) {
        this.parserFailureEmailTo = parserFailureEmailTo;
    }

    public Boolean getAutoCreateClientServiceId() {
        return autoCreateClientServiceId;
    }

    public void setAutoCreateClientServiceId(final Boolean autoCreateClientServiceId) {
        this.autoCreateClientServiceId = autoCreateClientServiceId;
    }
}
