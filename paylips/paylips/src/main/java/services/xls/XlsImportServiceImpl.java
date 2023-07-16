package services.xls;

import lombok.NoArgsConstructor;
import model.cargo.Cargo;
import model.cidade.Cidade;
import model.contato.Contato;
import model.endereco.Endereco;
import model.pais.Pais;
import model.pessoa.Pessoa;
import model.usuario.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import repositories.*;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class XlsImportServiceImpl implements IXlsImportService {

    private final Logger logger = LogManager.getLogger(XlsImportServiceImpl.class);
    private final PessoaRepository pessoaRepository;
    private final CargoRepository cargoRepository;
    private final ContatoRepository contatoRepository;
    private final CidadeRepository cidadeRepository;
    private final PaisRepository paisRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public XlsImportServiceImpl(PessoaRepository pessoaRepository, CargoRepository cargoRepository,
                                ContatoRepository contatoRepository, CidadeRepository cidadeRepository,
                                PaisRepository paisRepository, EnderecoRepository enderecoRepository,
                                UsuarioRepository usuarioRepository)
    {
        this.pessoaRepository = pessoaRepository;
        this.cargoRepository = cargoRepository;
        this.contatoRepository = contatoRepository;
        this.cidadeRepository = cidadeRepository;
        this.paisRepository = paisRepository;
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean importXls(final String fileName) {
        getXlsFile(fileName).ifPresent(workbook -> {
                List<Sheet> sheets = getSheets(workbook);
                List<Cargo> cargoList = salvaCargoLista(sheets.get(1));
                Sheet personSheet = sheets.get(0);
                List<XlsRow> pessoaRows = getRows(personSheet);

            pessoaRows.subList(1, pessoaRows.size()).forEach(row -> {
                    final long pessoaId = Double.valueOf(row.columns.get(0).toString()).longValue();
                    final String pessoaNome = row.columns.get(1).toString();
                    final String cidade = row.columns.get(2).toString();
                    final String email = row.columns.get(3).toString();
                    final String cep = row.columns.get(4).toString();
                    final String logradouro = row.columns.get(5).toString();
                    final String pais = row.columns.get(6).toString();
                    final String usuario = row.columns.get(7).toString();
                    final String telefone = row.columns.get(8).toString();
                    final String dataNascimento = row.columns.get(9).toString();
                    final String cargoId = row.columns.get(10).toString();
                    salvaPessoa(pessoaId, pessoaNome, cidade, email, cep, logradouro,
                            pais, usuario, telefone, dataNascimento, cargoId, cargoList);
                });
        });
        //pessoaRespository.findAll().forEach(System.out::println);

        return true;
    }

    private void salvaPessoa(
            final Long pessoaId, final String pessoaNome, final String cidadeNome,
            final String email, final String cep, final String logradouro,
            final String paisNome, final String usuarioNome, final String telefone,
            final String dataNascimento, final Object cargoId, final List<Cargo> cargoList
    ) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        pessoa.setNome(pessoaNome);
        pessoa.setDataNascimento(LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("M/d/yyyy")));

        final Contato contato = contatoRepository.findByEmailAndTelefone(email, telefone)
                .orElseGet(() -> contatoRepository.save(new Contato(email, telefone)));
        pessoa.setContato(contato);


        final Pais pais = paisRepository.findByName(paisNome)
                .orElseGet(() -> paisRepository.save(new Pais(paisNome)));

        final Cidade cidade = cidadeRepository.findByName(cidadeNome)
                .orElseGet(() -> cidadeRepository.save(new Cidade(cidadeNome, pais)));

        final Endereco endereco = enderecoRepository.findByLogradouroECep(logradouro, cep)
                .orElseGet(() -> enderecoRepository.save(new Endereco(logradouro, cep, cidade)));
        pessoa.setEndereco(endereco);

        final Usuario usuario = usuarioRepository.findByName(usuarioNome)
                .orElseGet(() -> usuarioRepository.save(new Usuario(usuarioNome)));
        pessoa.setUsuario(usuario);

        try {
            long roleIdLong = Double.valueOf(cargoId.toString()).longValue();
            cargoList.stream().filter(role -> role.getId().equals(roleIdLong)).forEach(pessoa::setCargo);
        } catch (NullPointerException nullPointerException) {
            logger.error("Person has no role");
        }
        pessoaRepository.save(pessoa);
    }

    private List<Cargo> salvaCargoLista(Sheet cargos) {
        List<Cargo> cargoList = new ArrayList<>();
        List<XlsRow> cargoRows = getRows(cargos);
        cargoRows.subList(1, cargoRows.size()).forEach(row -> {
            Cargo cargo = new Cargo();
            cargo.setId(Double.valueOf(row.columns.get(0).toString()).longValue());
            cargo.setNome(row.columns.get(1).toString());
            cargo.setSalario(new BigDecimal(row.columns.get(2).toString()));
            cargoRepository.save(cargo);
            cargoList.add(cargo);
        });
        return cargoList;
    }

    private Optional<Workbook> getXlsFile(final String fileName) {
        try {
            final File dataFile = new File(fileName);
            final FileInputStream input = new FileInputStream(dataFile);
            final Workbook workbook = WorkbookFactory.create(input);
            input.close();
            if (dataFile.isFile() && dataFile.exists()) {
                return Optional.of(workbook);
            }
        } catch (IOException ioException) {
            logger.error("Error on try load XLS file, cause: {}", ioException.getMessage());
        }
        return Optional.empty();
    }

    private Workbook loadWorkBook(FileInputStream input) {
        try {
            return WorkbookFactory.create(input);
        } catch (IOException e){
            logger.error("Error on try load xlsx workbook, cause: {}", e.getMessage());
        }
        return null;
    }

    private List<Sheet> getSheets(final Workbook workbook){
        final int numOfSheets = workbook.getNumberOfSheets();
        final List<Sheet> sheets = new ArrayList<>();
        IntStream.range(0, numOfSheets)
                .boxed()
                .forEach(page -> sheets.add(workbook.getSheetAt(page)));
        return sheets;
    }

    private List<XlsRow> getRows(Sheet sheet){
        final Iterator<Row> rowIterator = sheet.iterator();
        final List<XlsRow> xlsRowsArrayList = new ArrayList<>();
        while (rowIterator.hasNext()){
            final HSSFRow row = (HSSFRow) rowIterator.next();
            if (isRowEmpty(row)) {
                continue;
            }
            final Iterator<Cell> cellIterator = row.cellIterator();
            final XlsRow xlsRow = new XlsRow();
            while(cellIterator.hasNext()){
                final Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case STRING:
                        xlsRow.putColumns(cell.getColumnIndex(), cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        xlsRow.putColumns(cell.getColumnIndex(), cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        xlsRow.putColumns(cell.getColumnIndex(), cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        xlsRow.putColumns(cell.getColumnIndex(), cell.getCellFormula());
                        break;
                }
            }

            xlsRowsArrayList.add(xlsRow);
        }
        return xlsRowsArrayList;
    }

    private boolean isRowEmpty(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
    public static class XlsRow {
        private final Map<Integer, Object> columns = new HashMap<>();

        public void putColumns(int index, Object value) {
            columns.put(index, value);
        }

        public Object getValue(int columnIndex){
            return  columns.get(columnIndex);
        }

    }
}


