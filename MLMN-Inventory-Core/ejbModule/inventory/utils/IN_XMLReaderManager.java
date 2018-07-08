package inventory.utils;

import java.util.Hashtable;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class IN_XMLReaderManager {
	private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
	private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
	private static final IN_XMLReaderManager m_singletonManager = new IN_XMLReaderManager();

	/**
	 * Parser factory to be used to construct XMLReader objects
	 */
	private static SAXParserFactory m_parserFactory;

	/**
	 * Cache of XMLReader objects
	 */
	private ThreadLocal<XMLReader> m_readers;

	/**
	 * Keeps track of whether an XMLReader object is in use.
	 */
	private Hashtable<XMLReader, Boolean> m_inUse;

	/**
	 * Hidden constructor
	 */
	private IN_XMLReaderManager() {
	}

	/**
	 * Retrieves the singleton reader manager
	 */
	public static IN_XMLReaderManager getInstance() {
		return m_singletonManager;
	}

	/**
	 * Retrieves a cached XMLReader for this thread, or creates a new XMLReader,
	 * if the existing reader is in use. When the caller no longer needs the
	 * reader, it must release it with a call to {@link #releaseXMLReader}.
	 */
	public synchronized XMLReader getXMLReader() throws SAXException {
		XMLReader reader;

		if (m_readers == null) {
			// When the m_readers.get() method is called for the first time
			// on a thread, a new XMLReader will automatically be created.
			m_readers = new ThreadLocal<XMLReader>();
		}

		if (m_inUse == null) {
			m_inUse = new Hashtable<XMLReader, Boolean>();
		}

		// If the cached reader for this thread is in use, construct a new
		// one; otherwise, return the cached reader.
		reader = (XMLReader) m_readers.get();
		boolean threadHasReader = (reader != null);
		if (!threadHasReader || m_inUse.get(reader) == Boolean.TRUE) {
			try {
				try {
					// According to JAXP 1.2 specification, if a SAXSource
					// is created using a SAX InputSource the Transformer or
					// TransformerFactory creates a reader via the
					// XMLReaderFactory if setXMLReader is not used
					reader = XMLReaderFactory.createXMLReader();
				} catch (Exception e) {
					try {
						// If unable to create an instance, let's try to use
						// the XMLReader from JAXP
						if (m_parserFactory == null) {
							m_parserFactory = SAXParserFactory.newInstance();
							m_parserFactory.setNamespaceAware(true);
						}

						reader = m_parserFactory.newSAXParser().getXMLReader();
					} catch (ParserConfigurationException pce) {
						throw pce; // pass along pce
					}
				}
				try {
					reader.setFeature(NAMESPACES_FEATURE, true);
					reader.setFeature(NAMESPACE_PREFIXES_FEATURE, false);
				} catch (SAXException se) {
					// Try to carry on if we've got a parser that
					// doesn't know about namespace prefixes.
				}
			} catch (ParserConfigurationException ex) {
				throw new SAXException(ex);
			} catch (FactoryConfigurationError ex1) {
				throw new SAXException(ex1.toString());
			} catch (NoSuchMethodError ex2) {
			} catch (AbstractMethodError ame) {
			}

			// Cache the XMLReader if this is the first time we've created
			// a reader for this thread.
			if (!threadHasReader) {
				m_readers.set(reader);
				m_inUse.put(reader, Boolean.TRUE);
			}
		} else {
			m_inUse.put(reader, Boolean.TRUE);
		}

		return reader;
	}

	/**
	 * Mark the cached XMLReader as available. If the reader was not actually in
	 * the cache, do nothing.
	 * 
	 * @param reader
	 *            The XMLReader that's being released.
	 */
	public synchronized void releaseXMLReader(XMLReader reader) {
		// If the reader that's being released is the cached reader
		// for this thread, remove it from the m_isUse list.
		if (m_readers.get() == reader && reader != null) {
			m_inUse.remove(reader);
		}
	}
}
