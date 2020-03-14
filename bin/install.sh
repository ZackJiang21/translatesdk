#!/bin/bash


if ! [ -x "$(command -v python3)" ]; then
  echo 'Error: python is not installed.' >&2
  exit 1
else
  echo 'python installed'
fi

pymod=nltk

warn()
{
  echo "${bldred}Warning: $* $txtrst"
}

found()
{
  echo "${bldgre}$* found $txtrst"
}

if python -c "import $pymod" >/dev/null 2>&1
then
    found "$pymod"
else
    warn "$pymod: NOT FOUND"
    pip install nltk
    echo "$pymod installed"
fi

python -c 'import nltk; nltk.download("punkt")'
exit 0

