#!/bin/bash

# Run this with 'make generate-script'.

cd "$(dirname "$0")"

echo "Generating $1..."

cat > "$1" <<CAT
#!/bin/bash
export CLASSPATH="${BATIK_JARS}:${INSTALLDIR}"
java SVGToPNG "\$0" "\$@"
CAT

chmod 755 "$1"
