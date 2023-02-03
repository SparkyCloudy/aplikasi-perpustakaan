#!/bin/bash

# Root directory to search for .java files
root_dir=src

# Output directory for compiled class files
out_dir=out

# Classpath, including all .jar files in the current directory and the lib directory
classpath='lib/*:*.jar'

# Find all .java files in the root directory and its subdirectories
src_files=$(find $root_dir -type f -name "*.java")

# Create the output directory if it doesn't exist
if [ ! -d $out_dir ]; then
  mkdir $out_dir
fi

# Copy .env file
if [ ! "$(find $root_dir -name .env)" ]; then
  echo "File \".env\" not found, please create it inside \"src\" folder, you can read .env.example!"
  exit 1
fi

cp "src/.env" out/

# Compile each .java file
# shellcheck disable=SC2086
javac -d $out_dir -cp "$classpath" $src_files

# shellcheck disable=SC2181
if [ $? -ne 0 ]; then
  echo "--- Compilation failed ---"
  exit 1
fi

# Run the Main file
java -cp "$classpath":$out_dir Main