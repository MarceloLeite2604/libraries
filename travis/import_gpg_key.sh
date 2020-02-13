#!/bin/bash

openssl aes-256-cbc -K $encrypted_d894207b50dc_key -iv $encrypted_d894207b50dc_iv -in travis/libraries-key.asc.enc -out travis/libraries-key.asc -d
gpg --import travis/libraries-key.asc
