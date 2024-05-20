/********************************************************************************
 * Copyright (c) 2023, 2024 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ********************************************************************************/
package org.eclipse.jifa.server.domain.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jifa.server.enums.FileTransferMethod;
import org.eclipse.jifa.server.enums.FileType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * File Transfer Request
 */
@FileTransferRequest.Constraint
public class FileTransferRequest
{
    /**
     * File type, required
     */
    private FileType type;

    /**
     * Transfer method, required
     */
    private FileTransferMethod method;

    /**
     * Oss endpoint, required if method is OSS
     */
    private String ossEndpoint;

    /**
     * Oss access key id, required if method is OSS
     */
    private String ossAccessKeyId;

    /**
     * Oss secret access key, required if method is OSS
     */
    private String ossSecretAccessKey;

    /**
     * Oss bucket name, required if method is OSS
     */
    private String ossBucketName;

    /**
     * Oss object key, required if method is OSS
     */
    private String ossObjectKey;

    /**
     * S3 region, required if method is S3
     */
    private String s3Region;

    /**
     * S3 access key, required if method is S3
     */
    private String s3AccessKey;

    /**
     * S3 secret key, required if method is S3
     */
    private String s3SecretKey;

    /**
     * S3 bucket name, required if method is S3
     */
    private String s3BucketName;

    /**
     * S3 object key, required if method is S3
     */
    private String s3ObjectKey;

    /**
     * SCP hostname, required if method is SCP
     */
    private String scpHostname;

    /**
     * SCP user, required if method is SCP
     */
    private String scpUser;

    /**
     * SCP password, optional.
     * Public key authentication is used if password is not provided.
     */
    private String scpPassword;

    /**
     * SCP source path, required if method is SCP
     */
    private String scpSourcePath;

    /**
     * Url, required if method is URL
     */
    private String url;

    /**
     * Filename, required if method is TEXT
     */
    private String filename;

    /**
     * Text, required if method is TEXT
     */
    private String text;

    public FileType getType()
    {
        return type;
    }

    public FileTransferMethod getMethod()
    {
        return method;
    }

    public String getOssEndpoint()
    {
        return ossEndpoint;
    }

    public String getOssAccessKeyId()
    {
        return ossAccessKeyId;
    }

    public String getOssSecretAccessKey()
    {
        return ossSecretAccessKey;
    }

    public String getOssBucketName()
    {
        return ossBucketName;
    }

    public String getOssObjectKey()
    {
        return ossObjectKey;
    }

    public String getS3Region()
    {
        return s3Region;
    }

    public String getS3AccessKey()
    {
        return s3AccessKey;
    }

    public String getS3SecretKey()
    {
        return s3SecretKey;
    }

    public String getS3BucketName()
    {
        return s3BucketName;
    }

    public String getS3ObjectKey()
    {
        return s3ObjectKey;
    }

    public String getScpHostname()
    {
        return scpHostname;
    }

    public String getScpUser()
    {
        return scpUser;
    }

    public String getScpPassword()
    {
        return scpPassword;
    }

    public String getScpSourcePath()
    {
        return scpSourcePath;
    }

    public String getUrl()
    {
        return url;
    }

    public String getFilename()
    {
        return filename;
    }

    public String getText()
    {
        return text;
    }

    public void setType(FileType type)
    {
        this.type = type;
    }

    public void setMethod(FileTransferMethod method)
    {
        this.method = method;
    }

    public void setOssEndpoint(String ossEndpoint)
    {
        this.ossEndpoint = ossEndpoint;
    }

    public void setOssAccessKeyId(String ossAccessKeyId)
    {
        this.ossAccessKeyId = ossAccessKeyId;
    }

    public void setOssSecretAccessKey(String ossSecretAccessKey)
    {
        this.ossSecretAccessKey = ossSecretAccessKey;
    }

    public void setOssBucketName(String ossBucketName)
    {
        this.ossBucketName = ossBucketName;
    }

    public void setOssObjectKey(String ossObjectKey)
    {
        this.ossObjectKey = ossObjectKey;
    }

    public void setS3Region(String s3Region)
    {
        this.s3Region = s3Region;
    }

    public void setS3AccessKey(String s3AccessKey)
    {
        this.s3AccessKey = s3AccessKey;
    }

    public void setS3SecretKey(String s3SecretKey)
    {
        this.s3SecretKey = s3SecretKey;
    }

    public void setS3BucketName(String s3BucketName)
    {
        this.s3BucketName = s3BucketName;
    }

    public void setS3ObjectKey(String s3ObjectKey)
    {
        this.s3ObjectKey = s3ObjectKey;
    }

    public void setScpHostname(String scpHostname)
    {
        this.scpHostname = scpHostname;
    }

    public void setScpUser(String scpUser)
    {
        this.scpUser = scpUser;
    }

    public void setScpPassword(String scpPassword)
    {
        this.scpPassword = scpPassword;
    }

    public void setScpSourcePath(String scpSourcePath)
    {
        this.scpSourcePath = scpSourcePath;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Constraint of FileTransferRequest
     */
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    @jakarta.validation.Constraint(validatedBy = { Validator.class })
    @interface Constraint
    {
        String message() default "";

        Class[] groups() default {};

        Class[] payload() default {};
    }

    /**
     * Validator of FileTransferRequest
     */
    private static class Validator implements ConstraintValidator<Constraint, FileTransferRequest>
    {

        @Override
        public boolean isValid(FileTransferRequest request, ConstraintValidatorContext context)
        {
            String notNullTemplate = "{jakarta.validation.constraints.NotNull.message}";
            boolean valid = true;
            if (request.type == null)
            {
                valid = false;
                context.buildConstraintViolationWithTemplate(notNullTemplate)
                        .addPropertyNode("type")
                        .addConstraintViolation();
            }

            if (request.method == null)
            {
                context.buildConstraintViolationWithTemplate(notNullTemplate)
                        .addPropertyNode("method")
                        .addConstraintViolation();
                context.disableDefaultConstraintViolation();
                return false;
            }

            if (request.method == FileTransferMethod.UPLOAD)
            {
                context.disableDefaultConstraintViolation();
                return false;
            }

            switch (request.method)
            {
            case OSS ->
            {
                valid &= checkNotBlank(request.ossEndpoint, "ossEndpoint", context);
                valid &= checkNotBlank(request.ossAccessKeyId, "ossAccessKeyId", context);
                valid &= checkNotBlank(request.ossSecretAccessKey, "ossSecretAccessKey", context);
                valid &= checkNotBlank(request.ossBucketName, "ossBucketName", context);
                valid &= checkNotBlank(request.ossObjectKey, "ossObjectKey", context);
            }

            case S3 ->
            {
                valid &= checkNotBlank(request.s3Region, "s3Region", context);
                valid &= checkNotBlank(request.s3AccessKey, "s3AccessKey", context);
                valid &= checkNotBlank(request.s3SecretKey, "s3SecretKey", context);
                valid &= checkNotBlank(request.s3BucketName, "s3BucketName", context);
                valid &= checkNotBlank(request.s3ObjectKey, "s3ObjectKey", context);
            }

            case SCP ->
            {
                valid &= checkNotBlank(request.scpHostname, "scpHostname", context);
                valid &= checkNotBlank(request.scpUser, "scpUser", context);
                valid &= checkNotBlank(request.scpSourcePath, "scpSourcePath", context);
            }

            case URL -> valid &= checkNotBlank(request.url, "url", context);

            case TEXT ->
            {
                // TODO: should check file type
                valid &= checkNotBlank(request.filename, "filename", context);
                valid &= checkNotBlank(request.text, "text", context);
            }
            }

            if (!valid)
            {
                context.disableDefaultConstraintViolation();
            }
            return valid;
        }

        private boolean checkNotBlank(String value, String name, ConstraintValidatorContext context)
        {
            if (StringUtils.isBlank(value))
            {
                context.buildConstraintViolationWithTemplate("{jakarta.validation.constraints.NotBlank.message}")
                        .addPropertyNode(name)
                        .addConstraintViolation();
                return false;
            }
            return true;
        }

        private boolean checkTrue(String value, String name, ConstraintValidatorContext context)
        {
            if (StringUtils.isBlank(value))
            {
                context.buildConstraintViolationWithTemplate("{jakarta.validation.constraints.NotBlank.message}")
                        .addPropertyNode(name)
                        .addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}