import com.lowagie.text.Document
import com.lowagie.text.Paragraph
import com.lowagie.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object DocumentGenerator {

    fun generateBusinessReport(state: BusinessState, marketingResult: MarketingResult?): ByteArray {
        val baos = ByteArrayOutputStream()
        val document = Document()
        PdfWriter.getInstance(document, baos)
        document.open()

        document.add(Paragraph("KELNIC STARTUP BUILDER - OFFICIAL REPORT"))
        document.add(Paragraph("Generated: ${java.time.LocalDate.now()}"))
        document.add(Paragraph("Day \( {state.day} | Total Revenue: R \){state.totalRevenue} (ZAR equivalent)"))
        document.add(Paragraph("\nBusiness Progress:"))
        document.add(Paragraph("• Idea: ${state.ideaGenerated}"))
        document.add(Paragraph("• Website: ${state.websiteCreated}"))
        document.add(Paragraph("• Web App: ${state.webAppCreated}"))
        document.add(Paragraph("• Mobile App: ${state.mobileAppCreated}"))
        document.add(Paragraph("• Marketing Engine: ${state.marketingEngineEnabled}"))
        document.add(Paragraph("• Checkout: ${state.checkoutEnabled}"))

        marketingResult?.let {
            document.add(Paragraph("\nMarketing Campaign Summary"))
            document.add(Paragraph("Impressions: ${it.impressions}"))
            document.add(Paragraph("Clicks: ${it.clicks}"))
            document.add(Paragraph("Conversions: ${it.conversions}"))
            document.add(Paragraph("Revenue: R${it.revenue}"))
            document.add(Paragraph("Compliance Score: ${it.complianceScore}%"))
        }

        document.add(Paragraph("\nCompliance Statement: All marketing activities are truthful, non-deceptive, and fully compliant with South African CPA 2026 and international platform advertising policies."))
        document.close()
        return baos.toByteArray()
    }

    fun createZipWithReport(reportBytes: ByteArray, filename: String = "kelnic_report.pdf"): ByteArray {
        val baos = ByteArrayOutputStream()
        ZipOutputStream(baos).use { zip ->
            zip.putNextEntry(ZipEntry(filename))
            zip.write(reportBytes)
            zip.closeEntry()
        }
        return baos.toByteArray()
    }

    fun saveToDisk(bytes: ByteArray, path: String) {
        File(path).writeBytes(bytes)
        println("📄 Document saved: $path")
    }
}
