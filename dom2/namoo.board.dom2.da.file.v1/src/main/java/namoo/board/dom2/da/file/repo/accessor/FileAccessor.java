/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 4.
 */
package namoo.board.dom2.da.file.repo.accessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import namoo.board.dom2.da.file.repo.structure.ColumnValue;
import namoo.board.dom2.da.file.repo.structure.ColumnValueList;
import namoo.board.dom2.da.file.repo.structure.constant.FileConst;
import namoo.board.dom2.da.file.repo.util.IOUtils;

public class FileAccessor {
    //
    private File dataFile;
    private Indexer indexer;
    
    //--------------------------------------------------------------------------
    
    public FileAccessor(String indexFilePath, String dataFilePath) {
        //
        this.dataFile = new File(dataFilePath);
        this.indexer = new Indexer(new File(indexFilePath));
        
        createDataFile();
        indexer.createFile();
    }
    
    public FileAccessor(String dataFilePath) {
        //
        this.dataFile = new File(dataFilePath);
        createDataFile();
    }
    
    //------------------------------------------------------------------------
    
    /**
     * 인덱스를 관리하지 않는 파일의 데이터 저장
     * 
     * @param newColumnValues  저장할 컬럼값들
     */
    public void insertWithoutIndex(ColumnValueList newColumnValues) {
        //
        insert(newColumnValues);
    }
    
    /**
     * 인덱스를 관리하는 파일의 데이터 저장
     * 
     * @param newColumnValues  저장할 컬럼값들
     */
    public void insertWithIndex(ColumnValueList newColumnValues) {
        //
        indexer.createIndex(insert(newColumnValues));
    }
    
    /**
     * 데이터 저장 내부 매소드
     * 
     * @param newColumnValues  저장할 컬럼값들
     * @return {@link List}<{@link String}> 저장한 뒤의 파일 내 모든 csv 라인
     */
    private List<String> insert(ColumnValueList newColumnValues) {
        //
        RandomAccessFile raFile = null;
        BufferedReader bufferedReader = null;
        List<String> lines = new LinkedList<String>();
        
        try {
            // 파일의 마지막 라인에 데이터 append
            raFile = new RandomAccessFile(dataFile, "rw");
            
            long lastLinePosition = raFile.length();
            
            raFile.seek(lastLinePosition);
            raFile.write(newColumnValues.getDataCsvBytes());

            
            
            // 파일의 모든 csv 라인을 읽어 반환(index 파일 생성용)
            InputStream is = new FileInputStream(dataFile) ;
            Reader reader = new InputStreamReader(is, FileConst.FILE_ENCODING); 
            bufferedReader = new BufferedReader(reader, FileConst.BUFFER_SIZE);
            
            String lineStr = null;
            
            while ((lineStr = bufferedReader.readLine()) != null) {
                //
                lines.add(lineStr);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(raFile, bufferedReader);
        }
        return lines;
    }
    
    /**
     * 인덱스를 이용한 단일 데이터(CSV문자열) 조회
     * 
     * @param indexConditionColumnValue   조회조건 인덱스 컬럼값
     * @return {@link String} 조회 된 CSV 문자열
     */
    public String selectCsvByIndex(ColumnValue indexConditionColumnValue) {
        //
        Integer position = indexer.findDataPosition(indexConditionColumnValue);
        if (position == null) {
            return null;
        }
        
        RandomAccessFile raf = null;
        
        try {
            // 위치데이터(바이트)로 해당 라인 처음위치 접근
            raf = new RandomAccessFile(dataFile, "r");
            raf.seek(position);
            
            return IOUtils.parseUTF8Str(raf.readLine()); 
        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(raf);
        }
        return null;
    }

    /**
     * 인덱스를 이용하지 않은 다중(혹은 인덱스 관리대상이 아닌) 조건 단일 데이터 조회
     * 
     * @param conditionColumnValues    조회조건 컬럼값들
     * @return {@link String} 조회 된 CSV 문자열
     */
    public String selectOneCsv(ColumnValueList conditionColumnValues) {
        //
        BufferedReader br = null;
        
        try {
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FileConst.FILE_ENCODING);
            br = new BufferedReader(reader, FileConst.BUFFER_SIZE);
            
            String lineStr = null;
            
            // 파일의 처음부터 끝까지 순차탐색하며 라인을 읽는다.
            while ((lineStr = br.readLine()) != null) {
                //
                String[] datas = lineStr.split(FileConst.SEPARATOR);
                boolean equalValue = true;

                // 검색조건의 값과 모두 일치하면 반환
                for (ColumnValue columnValue : conditionColumnValues.getColumnValues()) {
                    //
                    String targetValue = datas[columnValue.getDataColumnSeq()].trim();
                    
                    if (!targetValue.equals(columnValue.getValue())) {
                        equalValue = false;
                        break;
                    }
                }
                if (equalValue) {
                    return lineStr;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(br);
        }
        return null;
    }
    
    /**
     * 인덱스를 이용하지 않은 다중(혹은 인덱스 관리대상이 아닌) 조건 데이터 목록 조회
     * 
     * @param conditionColumnValues    조회조건 컬럼값들
     * @return {@link List}<{@link String}>    조회 된 CSV 문자열 목록
     */
    public List<String> selectListCsv(ColumnValueList conditionColumnValues) {
        //
        BufferedReader br = null;
        List<String> csvResults = new ArrayList<String>();
        
        try {
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FileConst.FILE_ENCODING);
            br = new BufferedReader(reader, FileConst.BUFFER_SIZE);
            
            String lineStr = null;
            
            // 파일의 처음부터 끝까지 순차탐색하며 라인을 읽는다.
            while ((lineStr = br.readLine()) != null) {
                //
                String[] datas = lineStr.split(FileConst.SEPARATOR);
                boolean equalValue = true;

                // 검색조건의 값과 일치하지 않는 값이 하나라도 있는지 체크
                for (ColumnValue columnValue : conditionColumnValues.getColumnValues()) {
                    //
                    String targetValue = datas[columnValue.getDataColumnSeq()].trim();
                    
                    if (!targetValue.equals(columnValue.getValue())) {
                        equalValue = false;
                        break;
                    }
                }

                if (equalValue) {
                    csvResults.add(lineStr);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(br);
        }
        return csvResults;
    }
    
    /**
     * 데이터 파일의 모든 CSV 문자열 목록 조회
     * 
     * @return {@link List}<{@link String}>    조회 된 CSV 문자열 목록
     */
    public List<String> selectAll() {
        //
        BufferedReader br = null;
        List<String> csvResults = new ArrayList<String>();
        
        try {
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FileConst.FILE_ENCODING);
            br = new BufferedReader(reader, FileConst.BUFFER_SIZE);
            
            String line = null;
            
            while ((line = br.readLine()) != null) {
                csvResults.add(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(br);
        }
        return csvResults;
    }
    
    /**
     * 인덱스 관리 대상 데이터(csv문자열)의 수정
     * <pre>
     * updateColumnValues에서 CSV 문자열을 조회하여 라인을 덮어쓰고
     * 인덱스 정보를 새로 생성</pre>
     * 
     * @param updateColumnValues   수정할 컬럼값들
     */
    public void updateWithIndex(ColumnValueList updateColumnValues) {
        //
        Integer position = indexer.findDataPosition(updateColumnValues.getIndexColumnValue());
        if (position == null) {
            return;
        }
        indexer.createIndex(update(updateColumnValues));
    }
    
    /**
     * 인덱스 관리 대상이 아닌 데이터(csv문자열)의 수정
     * <pre>
     * updateColumnValues에서 CSV 문자열을 조회하여 라인을 덮어쓴다.</pre>
     * 
     * @param updateColumnValues   수정할 컬럼값들
     * 
     */
    public void updateWithoutIndex(ColumnValueList updateColumnValues) {
        //
        update(updateColumnValues);
    }
    
    /**
     * 데이터 수정 내부 메소드
     * 
     * @param updateColumnValues   수정할 컬럼값들
     * @return {@link List}<{@link String}>    수정한 후 파일내 모든 라인 목록
     */
    public List<String> update(ColumnValueList updateColumnValues) {
        //
        ColumnValue indexColumnValue = updateColumnValues.getIndexColumnValue();
        
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        List<String> newAllLines = new LinkedList<String>();  // 새로 생성할 인덱스를 위한 수정된 파일의 전체 csv 목록

        File newDataFile = null;
        
        try {
            // 수정할 라인을 제외하고 파일 전체를 복사해 새로운 파일을 생성
            newDataFile = File.createTempFile("temp_", ".temp");
            
            InputStream is = new FileInputStream(dataFile);
            Reader reader = new InputStreamReader(is, FileConst.FILE_ENCODING);
            bufferedReader = new BufferedReader(reader, FileConst.BUFFER_SIZE);
            
            OutputStream os = new FileOutputStream(newDataFile);
            Writer writer = new OutputStreamWriter(os, FileConst.FILE_ENCODING);
            bufferedWriter = new BufferedWriter(writer, FileConst.BUFFER_SIZE);
            
            
            String csvLine = null;
            
            while ((csvLine = bufferedReader.readLine()) != null) {
                //
                String[] columnDatas = csvLine.split(FileConst.SEPARATOR);
                
                // 수정 될 라인이면 새로운 데이터를 쓴다.
                if (columnDatas[FileConst.INDEX_COLUMN_SEQ].equals(indexColumnValue.getValue())) {
                    String updateCsv = updateColumnValues.getDataCsv();
                    bufferedWriter.write(updateCsv);
                    newAllLines.add(updateCsv);
                    continue;
                }
                newAllLines.add(csvLine);
                bufferedWriter.write(csvLine + FileConst.NEW_LINE);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedReader, bufferedWriter);
            dataFile.delete();
            newDataFile.renameTo(dataFile);
        }
        return newAllLines;
    }
    
    
    /**
     * 데이터 삭제
     * <pre>
     * 인덱스 관리대상 값(키값)으로 검색 후 데이터 삭제
     * 데이터의 삭제는 기존 파일에서 삭제할 데이터를 제외하고 새 파일을 생성
     * 데이터 삭제 후에는 인덱스를 지우고 새로 생성</pre>
     * 
     * @param indexConditionColumnValue    조회조건 인덱스 컬럼값
     */
    public void deleteByIndex(ColumnValue indexConditionColumnValue) {
        //
        Integer position = indexer.findDataPosition(indexConditionColumnValue);
        if (position == null) {
            return;
        }
        
        List<String> newAllLines = delete(new ColumnValueList(indexConditionColumnValue), true);
        indexer.createIndex(newAllLines);
    }
    
    /**
     * 데이터 삭제
     * <pre>
     * 다중 조회조건으로 검색하여 데이터 삭제
     * 데이터 삭제 후에는 인덱스를 지우고 새로 생성</pre>
     * 
     * @param conditionColumnValues    조회조건 컬럼값들
     */
    public void deleteByCondition(ColumnValueList conditionColumnValues) {
        //
        indexer.createIndex(delete(conditionColumnValues, false));
    }
    
    /**
     * 데이터 삭제 내부 메소드
     * <pre>인덱스 대상 여부가 true일 경우 인덱스값(키값)으로만 검색하고
     * false일 경우 모든 조회조건이 일치하는 대상을 검색</pre>
     * 
     * @param conditionColumnValues    조회조건 컬럼값들
     * @param indexical                인덱스 컬럼값만 비교할지 여부
     * @return {@link List}<{@link String}> 데이터가 삭제 된 후 파일의 전체 CSV 목록
     */
    private List<String> delete(ColumnValueList conditionColumnValues, boolean indexical) {
        //
        ColumnValue indexConditionColumnValue = conditionColumnValues.getIndexColumnValue();
        
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        List<String> newAllLines = new LinkedList<String>();
        
        File newDataFile = null;
        
        try {
            // 삭제할 라인을 제외하고 파일 전체를 복사해 새로운 파일을 생성
            newDataFile = File.createTempFile("temp_", ".temp");
            
            InputStream is = new FileInputStream(dataFile);
            Reader isr = new InputStreamReader(is, FileConst.FILE_ENCODING);
            bufferedReader = new BufferedReader(isr, FileConst.BUFFER_SIZE);
            
            OutputStream os = new FileOutputStream(newDataFile);
            Writer writer = new OutputStreamWriter(os, FileConst.FILE_ENCODING);
            bufferedWriter = new BufferedWriter(writer, FileConst.BUFFER_SIZE);
            
            
            String csvLine = null;
            
            while ((csvLine = bufferedReader.readLine()) != null) {
                //
                String[] columnDatas = csvLine.split(FileConst.SEPARATOR);
                boolean equals = false;
                
                // 인덱스 컬럼 값만 비교
                if (indexical) {
                    equals = columnDatas[FileConst.INDEX_COLUMN_SEQ].equals(indexConditionColumnValue.getValue()); 
                }
                // 모든 컬럼의 값 비교
                else {
                    equals = conditionColumnValues.checkEqualsAllColumnValue(columnDatas);
                }
                if (equals) {
                    continue;
                }
                
                newAllLines.add(csvLine);
                bufferedWriter.write(csvLine + FileConst.NEW_LINE);
            }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedReader, bufferedWriter);
            dataFile.delete();
            newDataFile.renameTo(dataFile);
        }
        return newAllLines;
    }
    
    //--------------------------------------------------------------------------
    
    /**
     * 데이터 저장 파일이 없으면 생성
     * 
     */
    private void createDataFile() {
        //
        // 디렉토리가 존재하지 않을 경우 생성
        File dir = new File(FileConst.STORE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // 파일이 존재하지 않을 경우 생성
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                System.out.println(dataFile.getName() + " file has been created.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
