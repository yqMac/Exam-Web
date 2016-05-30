<?xml version="1.0" encoding="GB2312"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:template name="Node">
	<xsl:param name="Index" />
	<xsl:param name="SubIndex" select="0" />
	<xsl:variable name="uppercase">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>
	<xsl:variable name="lowercase">abcdefghijklmnopqrstuvwxyz</xsl:variable>
	<xsl:variable name="title">
		<xsl:choose>
		<xsl:when test="@Memo">
			<xsl:value-of select="@Memo" />
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="@Title" />
		</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<a id="link_{$Index}_{$SubIndex}" style="font-size:12px;color:#FFFFFF;cursor:hand;text-decoration: none;">
		<xsl:attribute name="href">
			<xsl:choose>
				<xsl:when test="@Href!=''">
					<xsl:value-of select="@Href" />
				</xsl:when>
				<xsl:otherwise>
					javascript:CollapseNode(<xsl:value-of select="$Index" />);
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		<xsl:attribute name="target">
			<xsl:if test="@Target!=''">
				<xsl:value-of select="@Target" />
			</xsl:if>
		</xsl:attribute>
		<xsl:if test="@Href!=''">
			<xsl:attribute name="onClick">
				<xsl:choose>
					<xsl:when test="@Memo!=''">
						parent.navFrame.Postion.innerHTML = "<xsl:value-of select="@Memo" />";
					</xsl:when>
					<xsl:otherwise>
						parent.navFrame.Postion.innerHTML = "<xsl:value-of select="@Title" />";
					</xsl:otherwise>
				</xsl:choose>
				<xsl:choose>
					<xsl:when test="@TreeHref!=''">
						parent.document.getElementById('treeFrameset').cols="180,*";parent.treeFrame.location.href = '<xsl:value-of select="@TreeHref" />';
					</xsl:when>
					<xsl:otherwise>
						parent.document.getElementById('treeFrameset').cols="0,*";parent.treeFrame.location.href = 'about:blank';
					</xsl:otherwise>
				</xsl:choose>
			</xsl:attribute>
		</xsl:if>				
		<xsl:value-of select="@Title"/>
	</a>
	<xsl:if test="@Data!=''">
		<input type="hidden" id="data_{$Index}_{@Data}" value="{$SubIndex}" />
	</xsl:if>
</xsl:template>

<xsl:template match="/">
	<table width="150" border="0" cellspacing="0" cellpadding="2" bgcolor="#4B6CBD">
	<xsl:apply-templates/>
	</table>
</xsl:template>

<xsl:template match="Tree">
	<xsl:for-each select="TreeNode">
		<xsl:variable name="index" select="count(preceding-sibling::*)" />
		<tr>
			<td width="17%" height="21" align="center"><img src="images/index_left_ico.gif" width="13" height="13" border="0" /></td>
			<td><strong><xsl:call-template name="Node"><xsl:with-param name="Index" select="$index" /></xsl:call-template></strong></td>
			<td width="8%">
			<xsl:if test="count(child::node())>0">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" onMouseOver="style.cursor='hand'" onMouserOut="style.cursor='default'" id="img_table_{$index}">

					<tr id="nav_{$index}_0" style="display:block" onclick="CollapseNode({$index},false)">
						<td><img src="images/index_left_arrow_over.gif" width="12" height="12" /></td>
					</tr>
					<tr id="nav_{$index}_1" style="display:none" onclick="CollapseNode({$index},true)">
						<td><img src="images/index_left_arrow.gif" width="12" height="12" /></td>
					</tr>
				</table>
			</xsl:if>
			</td>
		</tr>
		<tbody id="sub_{$index}" style="display:none;">
		<xsl:for-each select="SubTreeNode">
			<tr>
			<td width="17%" height="21" align="center"></td>
			<td colspan='2'>
				<table width='100%' border='0' cellspacing='0' cellpadding='2'>
				<tr>
					<td width='12%'><div align='center'><img src='images/index_left_arrow_over_ico.gif' width='10' height='10' /></div></td>
					<td width='88%'>
					<xsl:call-template name="Node">
						<xsl:with-param name="Index" select="$index" />
						<xsl:with-param name="SubIndex" select="position()" />
					</xsl:call-template>
					</td>
				</tr>
				</table>	
			</td>
		  </tr>
		</xsl:for-each>
		</tbody>
		<tr>
		<td colspan="3"><img src="images/index_left_line.gif" width="137" height="2" /></td>
		</tr>
	</xsl:for-each>	
	<xsl:apply-templates/>
</xsl:template>

</xsl:stylesheet>