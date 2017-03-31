<?xml version="1.0" encoding="UTF-8"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"> 
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/> 

        <xsl:template match="*" > 
                <xsl:element name="{local-name()}"        namespace="{namespace-uri()}"> 
                        <xsl:for-each select="../@*"> 
                                <xsl:attribute name="{name()}" > 
                                        <xsl:value-of select="translate(. , $vLowercaseChars_CONST , $vUppercaseChars_CONST)"/> 
                                </xsl:attribute> 
                        </xsl:for-each> 
                        <xsl:apply-templates select="node()|@*"/> 
                </xsl:element> 
        </xsl:template> 

        <xsl:template match="text()" > 
                <xsl:value-of select="normalize-space(translate(. , $vLowercaseChars_CONST , $vUppercaseChars_CONST))"/> 
        </xsl:template> 

        <xsl:variable name="vLowercaseChars_CONST" select="'abcdefghijklmnopqrstuvwxyz'"/> 
        <xsl:variable name="vUppercaseChars_CONST" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/> 

</xsl:stylesheet> 